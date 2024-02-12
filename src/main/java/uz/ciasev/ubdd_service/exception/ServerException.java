package uz.ciasev.ubdd_service.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ServerException extends ApplicationException {

    public ServerException(String code) {
        this(code, null);
    }

    public ServerException(String code, String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, code, message);
    }
}
