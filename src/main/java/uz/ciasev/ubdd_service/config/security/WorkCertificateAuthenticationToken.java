package uz.ciasev.ubdd_service.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

public class WorkCertificateAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public WorkCertificateAuthenticationToken(String workCertificate, String password) {
        super(workCertificate, password, List.of());
    }
}
