package uz.ciasev.ubdd_service.mvd_core.api.signature.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckSignatureResponseDTO {

    @JsonAlias(value = "status")
    private Boolean isVerified;
}
