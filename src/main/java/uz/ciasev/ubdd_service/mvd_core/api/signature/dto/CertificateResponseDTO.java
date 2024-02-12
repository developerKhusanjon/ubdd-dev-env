package uz.ciasev.ubdd_service.mvd_core.api.signature.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateResponseDTO {

    private String serial;

    private LocalDateTime issuedOn;

    private LocalDateTime expiresOn;

}
