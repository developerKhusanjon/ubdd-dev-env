package uz.ciasev.ubdd_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.*;

@Getter
public class ValidationCollectingError extends ApplicationException {

    private List<String> errorCodes = new ArrayList<>();

    private List<String> messages = new ArrayList<>();

    public ValidationCollectingError() {
        super(HttpStatus.BAD_REQUEST, "VALIDATION_COLLECTING_ERROR", "Validation error");
    }

    @Override
    public String getDetail() {
        return "Validation errors: "+ String.join("; ", messages);
    }

    public boolean add(String errorCode) {
        return add(errorCode, errorCode);
    }

    public boolean add(String errorCode, String message) {
        if (Objects.nonNull(errorCode)) {
            errorCodes.add(errorCode);
            messages.add(message);
        }

        return Objects.nonNull(errorCode);
    }

    public boolean add(ApplicationException error) {
        errorCodes.add(error.getCode());
        messages.add(error.getDetail() == null ? error.getCode() : error.getDetail());
        return true;
    }

    public boolean add(Optional<String> errorCode) {
        errorCode.ifPresent(errorCodes::add);

        return errorCode.isPresent();
    }

    public boolean addIf(boolean condition, String errorCode) {
        return addIf(condition, errorCode, errorCode);
    }

    public boolean addIf(boolean condition, String errorCode, String message) {
        if (condition) {
            errorCodes.add(errorCode);
            messages.add(message);
        }

        return condition;
    }

    public boolean isEmpty() {
        return errorCodes.isEmpty();
    }

    public void throwError() {
        throw this;
    }

    public void throwErrorIfNotEmpty() {
        if (!isEmpty())
            throwError();
    }
}
