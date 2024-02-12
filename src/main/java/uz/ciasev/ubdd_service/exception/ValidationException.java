package uz.ciasev.ubdd_service.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ValidationException extends ApplicationException {

    public ValidationException(String code) {
        this(code, null);
    }

    public ValidationException(String code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }
}
