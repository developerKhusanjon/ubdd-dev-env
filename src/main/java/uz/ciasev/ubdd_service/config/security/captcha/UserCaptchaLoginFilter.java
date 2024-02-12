package uz.ciasev.ubdd_service.config.security.captcha;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import uz.ciasev.ubdd_service.config.security.UserJWTService;
import uz.ciasev.ubdd_service.config.security.UserLoginFilter;
import uz.ciasev.ubdd_service.config.security.exception.CiasevAuthenticationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.UserLoginTimeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserCaptchaLoginFilter<T> extends UserLoginFilter<T> {

    private final CaptchaLoginRequestMapper<T> loginRequestMapper;

    public UserCaptchaLoginFilter(
            AuthenticationManager authenticationManager,
            ObjectMapper objectMapper,
            String url,
            UserJWTService jwtService,
            CaptchaLoginRequestMapper<T> loginRequestMapper,
            UserLoginTimeService userLoginTimeService) {
        super(
                authenticationManager,
                objectMapper,
                url,
                jwtService,
                loginRequestMapper,
                userLoginTimeService);
        this.loginRequestMapper = loginRequestMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        T body = parseRequest(request, loginRequestMapper.getBodyClass());
        String captcha = String.valueOf(request.getParameter("session_id"));
        if (!loginRequestMapper.validateCredentials(body, captcha)) {
            throw new CiasevAuthenticationException(ErrorCode.AUTH_ERROR_INVALID_CREDENTIALS, "Invalid credentials");
        }

        Authentication authentication = loginRequestMapper.build(body);


        return authenticationManager.authenticate(authentication);
    }
}