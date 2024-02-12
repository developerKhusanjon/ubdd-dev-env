package uz.ciasev.ubdd_service.mvd_core.api.signature.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckSignatureRequestDTO {

    private final String digest;
    private final String signature;
}
