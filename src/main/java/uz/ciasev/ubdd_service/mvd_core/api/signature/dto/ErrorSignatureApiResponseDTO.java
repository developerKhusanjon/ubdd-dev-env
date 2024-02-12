package uz.ciasev.ubdd_service.mvd_core.api.signature.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorSignatureApiResponseDTO {

    private final String code;

    private final String message;
}
