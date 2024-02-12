package uz.ciasev.ubdd_service.dto.internal.response.signature;

import lombok.Getter;
import uz.ciasev.ubdd_service.service.signatre.dto.DigitalSignatureCertificateDTO;

@Getter
public class MyDigitalSignatureCertificateCreateResponseDTO extends MyDigitalSignatureCertificateResponseDTO {

    private final boolean isSmsSent;

    public MyDigitalSignatureCertificateCreateResponseDTO(DigitalSignatureCertificateDTO certificate, boolean isSmsSent) {
        super(certificate);
        this.isSmsSent = isSmsSent;
    }
}
