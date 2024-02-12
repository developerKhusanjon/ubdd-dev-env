package uz.ciasev.ubdd_service.config.security;

import org.springframework.security.core.Authentication;

public class WorkCertificateLoginRequestMapper implements LoginRequestMapper<WorkCertificateLoginDTO> {


    @Override
    public Authentication build(WorkCertificateLoginDTO body) {
        return new WorkCertificateAuthenticationToken(body.getWorkCertificate(), body.getPassword());
    }

    @Override
    public Class getBodyClass() {
        return WorkCertificateLoginDTO.class;
    }

    @Override
    public boolean validateCredentials(WorkCertificateLoginDTO body) {
        return body != null
                && body.getWorkCertificate() != null
                && body.getPassword() != null
                && !body.getWorkCertificate().isEmpty();
    }

    @Override
    public String loginType() {
        return "gaiWorkCertificate";
    }

}
