package uz.ciasev.ubdd_service.mvd_core.api.court.dto.seventh;

import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResultDTO;

@Data
public class CourtDecisionFileWrapperDTO {

    private CourtDecisionFileResponseDTO decisionFile;
    private CourtResultDTO result;
    private Long envelopeId;
}
