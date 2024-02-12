
package uz.ciasev.ubdd_service.config.controller_advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.ciasev.ubdd_service.mvd_core.api.court.controller.CourtExternalApiController;
import uz.ciasev.ubdd_service.config.wrapper.IgnoreResponseBinding;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResultDTO;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.court.CourtResult;
import uz.ciasev.ubdd_service.exception.court.CourtWrappedException;
import uz.ciasev.ubdd_service.exception.court.ExternalException;

import java.util.List;

@Slf4j
@Order(0)
@ControllerAdvice(assignableTypes = {CourtExternalApiController.class})
@RequiredArgsConstructor
public class CourtExceptionHandler extends CiasevRestExceptionHandler {

    protected ResponseEntity getFail(HttpStatus status,
                                     String code,
                                     String message,
                                     List<String> validationCodes,
                                     String ex) {
        return buildResponse(
                CourtResult.VALIDATION_ERROR,
                buildMessage(code, message, validationCodes),
                null
        );

    }

    @IgnoreResponseBinding
    @ExceptionHandler(ExternalException.class)
    private ResponseEntity<Object> handleExternalException(ExternalException ex) {

        return buildResponse(
                ex.getResult(),
                ex.getDescription(),
                ex.getEnvelopeId()
        );
    }

    @IgnoreResponseBinding
    @ExceptionHandler(CourtWrappedException.class)
    private ResponseEntity<Object> handleCourtWrappedException(CourtWrappedException ex) {

        ApplicationException appEx = ex.getApplicationException();

        return buildResponse(
                ex.getResult(),
                buildMessage(appEx.getCode(), appEx.getDetail(), null),
                ex.getEnvelopeId()
        );
    }

    private ResponseEntity<Object> buildResponse(CourtResult result, String message, Long envelopedId) {
        return new ResponseEntity<>(
                new CourtResponseDTO(
                        new CourtResultDTO(result, message),
                        envelopedId
                ),
                HttpStatus.OK
        );
    }

    private String buildMessage(String code,
                                String message,
                                List<String> validationCodes) {

        StringBuilder errorMessageBuilder = new StringBuilder();

        if (code != null) errorMessageBuilder.append(code);
        if (message != null) errorMessageBuilder.append(": ").append(message);
        if (validationCodes != null) errorMessageBuilder.append(validationCodes);

        return errorMessageBuilder.toString();
    }
}
