package uz.ciasev.ubdd_service.mvd_core.api.court.dto.seventh;

import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtBaseDTO;

import javax.validation.constraints.NotNull;

@Data
public class CourtDecisionFileRequestDTO implements CourtBaseDTO {

    @NotNull(message = "CASE_ID_REQUIRED")
    private Long caseId;

    @NotNull(message = "CLAIM_ID_REQUIRED")
    private Long claimId;

    @NotNull(message = "FILE_ID_REQUIRED")
    private Long fileId;

    @NotNull(message = "URI_REQUIRED")
    private String uri;

    private boolean review;
}
