package uz.ciasev.ubdd_service.config.security;

import org.apache.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static uz.ciasev.ubdd_service.config.security.SecurityConstants.TOKEN_PREFIX;

public class AuthHeaderHelper {

    public  static Optional<String> getToken(HttpServletRequest request) {
        String tokenValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (tokenValue == null) return Optional.empty();

        String token = tokenValue.replace(TOKEN_PREFIX, "");

        if (token.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(token);
    }
}
