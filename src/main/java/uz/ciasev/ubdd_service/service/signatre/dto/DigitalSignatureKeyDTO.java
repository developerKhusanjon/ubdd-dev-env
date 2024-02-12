package uz.ciasev.ubdd_service.service.signatre.dto;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureCertificate;

@Getter
public class DigitalSignatureKeyDTO extends DigitalSignatureCertificateDTO {

    private final String privateKey;

    public DigitalSignatureKeyDTO(DigitalSignatureCertificate certificate, String privateKey) {
        super(certificate, false);
        this.privateKey = privateKey;
    }
}
