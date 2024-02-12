package uz.ciasev.ubdd_service.config.security.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

public class CiasevAuthenticationException extends AuthenticationException implements CiasevAuthenticationError {

    @Getter
    private final String code;

    public CiasevAuthenticationException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
