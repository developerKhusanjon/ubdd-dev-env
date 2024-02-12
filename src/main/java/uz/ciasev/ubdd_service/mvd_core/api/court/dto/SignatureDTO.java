package uz.ciasev.ubdd_service.mvd_core.api.court.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignatureDTO {

    private byte[] signatureValue;
    private byte[] signedPublicCertificate;
    private LocalDate signedDate;
    private byte[] signedControl;
}
