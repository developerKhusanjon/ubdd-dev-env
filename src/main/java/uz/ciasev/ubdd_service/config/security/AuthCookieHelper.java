package uz.ciasev.ubdd_service.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

import static uz.ciasev.ubdd_service.config.security.SecurityConstants.TOKEN_COOKIE_NAME;

public class AuthCookieHelper {

    public static Optional<String> getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return Optional.empty();

        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(TOKEN_COOKIE_NAME))
                .map(Cookie::getValue)
                .filter(t -> !t.isBlank())
                .findFirst();
    }

    public static LogoutConfigurer<HttpSecurity> configureLogout(LogoutConfigurer<HttpSecurity> http) {
        return http.deleteCookies(TOKEN_COOKIE_NAME);
    }

    public static void setToken(HttpServletResponse response, JWTToken token) {
//        Cookie authCookie = new Cookie(TOKEN_COOKIE_NAME, token.getToken());
//        //  Закоменти что бы отключить httpOnly
//        authCookie.setHttpOnly(true);
//        authCookie.setMaxAge(token.getExpiresAt());
//        authCookie.setPath("/");
////        authCookie.setDomain(request.getHeader("Host"));
//
//        response.addCookie(authCookie);

        response.setHeader(
                "Set-Cookie",
                String.format("%s=%s; Max-Age=%s; Path=/; SameSite=Lax; HttpOnly",
                        TOKEN_COOKIE_NAME,
                        token.getToken(),
                        token.getExpiresAt())
        );
    }
}
