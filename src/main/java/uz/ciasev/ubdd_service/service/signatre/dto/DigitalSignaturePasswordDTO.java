package uz.ciasev.ubdd_service.service.signatre.dto;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.signature.DigitalSignatureCertificate;

@Getter
public class DigitalSignaturePasswordDTO extends DigitalSignatureCertificateDTO {

    private String password;

    public DigitalSignaturePasswordDTO(DigitalSignatureCertificate certificate, String password) {
        super(certificate, false);
        this.password = password;
    }

}
