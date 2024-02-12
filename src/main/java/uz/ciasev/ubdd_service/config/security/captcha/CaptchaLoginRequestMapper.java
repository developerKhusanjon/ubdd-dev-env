package uz.ciasev.ubdd_service.config.security.captcha;

import uz.ciasev.ubdd_service.config.security.LoginRequestMapper;

public interface CaptchaLoginRequestMapper<T> extends LoginRequestMapper<T> {

    boolean validateCredentials(T body, String params);
}
