package uz.ciasev.ubdd_service.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

public class UsernameAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public UsernameAuthenticationToken(String username, String password) {
        super(username, password, List.of());
    }
}
