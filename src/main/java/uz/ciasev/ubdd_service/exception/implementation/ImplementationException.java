package uz.ciasev.ubdd_service.exception.implementation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ImplementationException extends ApplicationException {

    public ImplementationException(String message) {
        this(ErrorCode.IMPLEMENTATION_ERROR, message);
    }

    public ImplementationException(String code, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, code, message);
    }
}
