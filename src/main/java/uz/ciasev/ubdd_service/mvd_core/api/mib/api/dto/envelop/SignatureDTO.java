package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.envelop;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignatureDTO {

    @JsonProperty("signatureValue")
    private byte[] value;

    @JsonProperty("signedPubicCertificate")
    private byte[] pubicCertificate;

    @JsonProperty("signedDate")
    private LocalDate date;

    @JsonProperty("signedControl")
    private byte[] control;
}
