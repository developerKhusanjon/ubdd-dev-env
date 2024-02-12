package uz.ciasev.ubdd_service.mvd_core.api.court.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtResponseDTO {

    @JsonProperty(value = "Result")
    private CourtResultDTO result;
    private Long envelopeId;
}
