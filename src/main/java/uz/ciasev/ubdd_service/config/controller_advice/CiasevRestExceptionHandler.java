package uz.ciasev.ubdd_service.config.controller_advice;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.NonUniqueResultException;
import org.hibernate.TransactionException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationCollectingError;
import uz.ciasev.ubdd_service.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public abstract class CiasevRestExceptionHandler extends ResponseEntityExceptionHandler {

    abstract protected ResponseEntity getFail(HttpStatus status,
                                              String code,
                                              String message,
                                              List<String> validationCodes,
                                              String ex);


    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleBaseException(Exception ex, HttpServletRequest request) {

        log.error(String.format("Url '%s' throws error '%s'", request.getRequestURI(), ex.getClass().getSimpleName()),
                ex);

        String errorClassName = ex.getClass().getSimpleName();
        StackTraceElement stackTraceElement = Arrays.stream(ex.getStackTrace())
                .filter(st -> st.getClassName().contains("uz.ciasev.ubdd_service"))
                .findFirst().orElseGet(() -> ex.getStackTrace()[0]);
        String rootClassName = StringUtils.splitAndGetLast(stackTraceElement.getClassName(), "\\.");
        Integer rootLineNumber = stackTraceElement.getLineNumber();

        String errorCode = String.format("%s_in_%s:%s", errorClassName, rootClassName, rootLineNumber);
        String message = String.format("Error %s happened in class %s (%s line %s)", errorClassName, rootClassName, stackTraceElement.getFileName(), rootLineNumber);

        return getFail(HttpStatus.INTERNAL_SERVER_ERROR,
                errorCode,
                message,
                null,
                ex.getLocalizedMessage());
    }


    @ExceptionHandler(AmazonS3Exception.class)
    private ResponseEntity<Object> handleAmazonS3Exception(AmazonS3Exception ex) {
        if ("NoSuchKey".equals(ex.getErrorCode())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return getFail(HttpStatus.SERVICE_UNAVAILABLE,
                ErrorCode.FILE_SERVICE_ERROR,
                ex.getMessage(),
                null,
                null);
    }

//    @ExceptionHandler(IOException.class)
//    private ResponseEntity<Object> handleIOException(IOException ex, WebRequest request) {
//
//        log.error("IO ERROR {}, {}",
//                request.getHeader("host"),
//                ex.getLocalizedMessage());
//
//        return getFail(HttpStatus.INTERNAL_SERVER_ERROR,
//                ErrorCode.IO_ERROR,
//                ex.getMessage(),
//                null,
//                null);
//    }

    @ExceptionHandler(ApplicationException.class)
    private ResponseEntity<Object> handleApplicationException(ApplicationException ex) {

        return getFail(ex.getStatus(),
                ex.getCode(),
                ex.getDetail(),
                null,
                null);
    }

    @ExceptionHandler(ValidationCollectingError.class)
    private ResponseEntity<Object> handleValidationCollectingError(ValidationCollectingError ex) {

        return getFail(ex.getStatus(),
                null,
                ex.getDetail(),
                ex.getErrorCodes(),
                null);
    }

    @ExceptionHandler(NonUniqueResultException.class)
    private ResponseEntity<Object> handleNonUniqueResultException(NonUniqueResultException ex) {

        return getFail(HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.QUERY_RETURN_NOT_UNIQUE_RESULT,
                "DB content error",
                null,
                ex.getMessage());
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    private ResponseEntity<Object> handleDBConstraintViolationException(org.hibernate.exception.ConstraintViolationException ex) {

        return getFail(HttpStatus.BAD_REQUEST,
                ErrorCode.CONSTRAINT_VIOLATION,
                null,
                null,
                ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        return getFail(HttpStatus.BAD_REQUEST,
                ErrorCode.REQUIRED_PARAMS_TYPE_INVALID,
                ex.getMessage(),
                null,
                null);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    private ResponseEntity<Object> handleConstraintViolationException(javax.validation.ConstraintViolationException ex) {
        List<String> fieldErrors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        return getFail(HttpStatus.BAD_REQUEST,
                null,
                "Validation error",
                fieldErrors,
                null);
    }

    @ExceptionHandler(MultipartException.class)
    private ResponseEntity handleMultipartException(MultipartException ex) {

        return getFail(HttpStatus.BAD_REQUEST,
                ErrorCode.REQUEST_IS_NOT_MULTIPART,
                "Current request is not a multipart request. Try check request content type is 'multipart/form-data' or file present in request",
                null,
                null);
    }

    @ExceptionHandler(TransactionTimedOutException.class)
    private ResponseEntity handleTransactionTimedOutException(TransactionTimedOutException ex) {

        return getFail(HttpStatus.REQUEST_TIMEOUT,
                ErrorCode.REQUEST_TIMED_OUT,
                "Request timed out",
                null,
                null);
    }

    @ExceptionHandler(TransactionException.class)
    private ResponseEntity handleTransactionException(TransactionException ex) {
        if (ex.getMessage().equals("transaction timeout expired")) {
            return getFail(HttpStatus.REQUEST_TIMEOUT,
                    ErrorCode.REQUEST_TIMED_OUT,
                    ex.getMessage(),
                    null,
                    null);
        }

        return getFail(HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.TRANSACTION_ERROR,
                ex.getMessage(),
                null,
                ex.getLocalizedMessage());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {

        log.error("UNKNOWN_ERROR {}:{}", ex.getClass().getName(), ex.getMessage());

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        if (Objects.nonNull(body)) {
            return new ResponseEntity<>(body, headers, status);
        }

        return getFail(status,
                "UNKNOWN_ERROR",
                "Unknown error",
                null,
                ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
            AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

        return getFail(HttpStatus.REQUEST_TIMEOUT,
                ErrorCode.REQUEST_TIMED_OUT,
                "Request timed out",
                null,
                null);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> fieldErrors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<String> objectErrors = ex.getBindingResult()
                .getGlobalErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        fieldErrors.addAll(objectErrors);

        return getFail(HttpStatus.BAD_REQUEST,
                null,
                "Method argument not valid",
                fieldErrors,
                null);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return getFail(HttpStatus.BAD_REQUEST,
                ErrorCode.REQUIRED_PARAMS_MISSING,
                ex.getMessage(),
                null,
                null);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return getFail(HttpStatus.BAD_REQUEST,
                ErrorCode.REQUIRED_PART_MISSING,
                ex.getMessage(),
                null,
                null);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {


        Throwable rootEx = ex.getRootCause();
        if (rootEx instanceof ApplicationException) {
            ApplicationException typedEx = (ApplicationException) rootEx;
            return getFail(typedEx.getStatus(),
                    typedEx.getCode(),
                    typedEx.getDetail(),
                    null,
                    null);
        }

        if (rootEx instanceof DateTimeParseException) {
            DateTimeParseException typedEx = (DateTimeParseException) rootEx;
            return getFail(HttpStatus.BAD_REQUEST,
                    ErrorCode.DATE_TIME_FORMAT_INVALID,
                    typedEx.getMessage(),
                    null,
                    null);
        }

        if (rootEx instanceof InvalidFormatException) {
            InvalidFormatException typedEx = (InvalidFormatException) rootEx;
            return getFail(HttpStatus.BAD_REQUEST,
                    ErrorCode.VALUE_FORMAT_INVALID,
                    typedEx.getMessage(),
                    null,
                    null);
        }


        log.error("ERROR {}:{}, {}, {}, {}", ex.getClass().getSimpleName(), ex.getLocalizedMessage(),
                request.getHeader("host"),
                request.getHeader("user-agent"),
                request.getDescription(true));

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return getFail(status,
                "MESSAGE_NOT_READABLE",
                ex.getLocalizedMessage(),
                null,
                ex.getLocalizedMessage());
    }
}
