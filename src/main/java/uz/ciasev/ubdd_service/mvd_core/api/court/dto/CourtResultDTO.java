package uz.ciasev.ubdd_service.mvd_core.api.court.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import uz.ciasev.ubdd_service.exception.court.CourtResult;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CourtResultDTO implements Serializable {

    @JsonProperty(value = "Result")
    private Long result;

    private String resultDescription;

    public CourtResultDTO(CourtResult result, String resultDescription) {
        this.result = result.getValue();
        this.resultDescription = resultDescription;
    }

    @JsonIgnore
    public boolean isSuccessfully() {
        return CourtResult.SUCCESSFULLY.getValue().equals(result);
    }
}
