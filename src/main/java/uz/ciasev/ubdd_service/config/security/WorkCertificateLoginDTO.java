package uz.ciasev.ubdd_service.config.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkCertificateLoginDTO {

    private String workCertificate;
    private String password;
}
