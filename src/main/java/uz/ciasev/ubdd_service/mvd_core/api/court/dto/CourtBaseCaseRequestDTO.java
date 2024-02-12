package uz.ciasev.ubdd_service.mvd_core.api.court.dto;

import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.first.*;

import java.util.List;

@Data
public class CourtBaseCaseRequestDTO {

    private Long caseId;

    private Long investigatingOrg;
    private String investigatedOrgName;
    private Long investigatedOrgId;
    private String investigatorName;

    private boolean hasDecision = false;

    private List<FirstCourtEvidenceRequestDTO> evidenceList;
    private List<FirstCourtDefendantRequestDTO> defendant;
    private List<FirstCourtClaimantRequestDTO> claimant;
    private List<FirstCourtParticipantRequestDTO> participants;
    private List<FirstCourtFileRequestDTO> files;
    private List<FirstCourtLawyerRequestDTO> lawyer;
}
