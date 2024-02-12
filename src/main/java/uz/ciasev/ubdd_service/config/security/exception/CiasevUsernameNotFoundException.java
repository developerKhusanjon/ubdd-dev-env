package uz.ciasev.ubdd_service.config.security.exception;

import lombok.Getter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CiasevUsernameNotFoundException extends UsernameNotFoundException implements CiasevAuthenticationError {

    @Getter
    private final String code;

    public CiasevUsernameNotFoundException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
