package uz.ciasev.ubdd_service.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@EqualsAndHashCode(callSuper = true)
public class NotFoundException extends ApplicationException {

    public NotFoundException(String code) {
        this(code, null);
    }

    public NotFoundException(String code, String message) {
        super(HttpStatus.NOT_FOUND, code, message);
    }
}
