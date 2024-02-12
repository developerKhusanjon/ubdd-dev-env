package uz.ciasev.ubdd_service.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ForbiddenException extends ApplicationException {

    public ForbiddenException(String code, String message) {
        super(HttpStatus.FORBIDDEN, code, message);
    }
}
