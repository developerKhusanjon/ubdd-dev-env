package uz.ciasev.ubdd_service.service.court.methods;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.first.*;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;

import java.util.List;

public interface CourtGeneralSendingService {

    List<FirstCourtFileRequestDTO> buildFiles(Long admCaseId);

    List<FirstCourtEvidenceRequestDTO> buildEvidence(Long admCaseId);

    List<FirstCourtClaimantRequestDTO> buildClaimants(Long admCaseId);

    List<FirstCourtParticipantRequestDTO> buildParticipants(Long admCaseId);

    List<FirstCourtDefendantRequestDTO> buildDefendants(AdmCase admCase, Resolution resolution);

    List<FirstCourtCauseDamageRequestDTO> buildCausedDamage(Long admCaseId, Long violatorId);
}
