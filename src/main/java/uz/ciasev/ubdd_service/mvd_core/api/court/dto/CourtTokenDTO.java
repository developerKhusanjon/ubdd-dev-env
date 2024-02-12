package uz.ciasev.ubdd_service.mvd_core.api.court.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CourtTokenDTO {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private Long expiresIn;
    @JsonProperty("token_type")
    private String tokenType;
}
