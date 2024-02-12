package uz.ciasev.ubdd_service.mvd_core.api.court;

import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResultDTO;

import java.util.Optional;

@Data
public class CourtSendResult {

    private final boolean isSuccess;

    private final String message;

    private final CourtResponseDTO response;

    public CourtSendResult(CourtResponseDTO response) {
        Optional<CourtResultDTO> resultOpt = Optional.ofNullable(response).map(CourtResponseDTO::getResult);

        this.response = response;

        if (resultOpt.isPresent()) {
            var result = resultOpt.get();
            isSuccess = result.isSuccessfully();
            message = result.getResultDescription();
        } else {
            isSuccess = false;
            message = "Empty court response body";
        }
    }
}
