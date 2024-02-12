package uz.ciasev.ubdd_service.mvd_core.api.signature.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDetailResponseDTO extends CertificateResponseDTO {

    private String ownerName;

    private String privateKey;

}
