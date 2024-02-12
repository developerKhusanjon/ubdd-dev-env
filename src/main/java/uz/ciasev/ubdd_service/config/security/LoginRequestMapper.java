package uz.ciasev.ubdd_service.config.security;

import org.springframework.security.core.Authentication;

public interface LoginRequestMapper<T> {

    Authentication build(T body);

    Class<T> getBodyClass();

    boolean validateCredentials(T body);

    String loginType();
}
