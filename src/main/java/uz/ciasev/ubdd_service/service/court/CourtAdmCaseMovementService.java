package uz.ciasev.ubdd_service.service.court;

import uz.ciasev.ubdd_service.mvd_core.api.court.dto.second.CourtRegistrationStatusRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.court.CourtAdmCaseMovementCountResponse;
import uz.ciasev.ubdd_service.dto.internal.response.court.CourtAdmCaseMovementResponseDTO;
import uz.ciasev.ubdd_service.entity.court.CourtAdmCaseMovement;
import uz.ciasev.ubdd_service.entity.court.CourtMovementAlias;

import java.util.List;

public interface CourtAdmCaseMovementService {

    CourtAdmCaseMovement save(CourtAdmCaseMovement caseMovement);

    CourtAdmCaseMovement save(ThirdCourtResolutionRequestDTO resolution, Long claimId, CourtMovementAlias alias);

    CourtAdmCaseMovement save(CourtRegistrationStatusRequestDTO registrationDTO);

    void saveWithNewTransaction(CourtAdmCaseMovement caseMovement);

    List<CourtAdmCaseMovementResponseDTO> findAllByAdmCaseId(Long id);

    List<CourtAdmCaseMovement> findAllByCaseAndClaimIds(Long caseId, Long claimId);

    boolean hasThirdMethod(Long caseId, Long claimId);

    CourtAdmCaseMovementCountResponse findAdmCaseToCourtSentCount(Long caseId);
}
