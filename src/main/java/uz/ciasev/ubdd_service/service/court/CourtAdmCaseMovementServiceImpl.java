package uz.ciasev.ubdd_service.service.court;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.second.CourtRegistrationStatusRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.court.CourtAdmCaseMovementCountResponse;
import uz.ciasev.ubdd_service.dto.internal.response.court.CourtAdmCaseMovementResponseDTO;
import uz.ciasev.ubdd_service.entity.court.CourtAdmCaseMovement;
import uz.ciasev.ubdd_service.entity.court.CourtMovementAlias;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.repository.court.CourtAdmCaseMovementRepository;
import uz.ciasev.ubdd_service.utils.PageUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourtAdmCaseMovementServiceImpl implements CourtAdmCaseMovementService {

    private final CourtAdmCaseMovementRepository courtAdmCaseMovementRepository;

    @Override
    public CourtAdmCaseMovement save(CourtAdmCaseMovement caseMovement) {
        return courtAdmCaseMovementRepository.save(caseMovement);
    }

    @Override
    public CourtAdmCaseMovement save(ThirdCourtResolutionRequestDTO resolution, Long claimId, CourtMovementAlias alias) {
        var caseId = resolution.getCaseId();

        var caseMovement = new CourtAdmCaseMovement();
        caseMovement.setCaseId(caseId);
        caseMovement.setClaimId(claimId);
        caseMovement.setStatusId(resolution.getStatus());
        caseMovement.setStatusTime(resolution.getHearingDate());
        caseMovement.setAlias(alias);

        return save(caseMovement);
    }

    @Override
    public CourtAdmCaseMovement save(CourtRegistrationStatusRequestDTO registrationDTO) {
        LocalDate statusDate = registrationDTO.getStatus().is(CourtStatusAlias.REGISTERED_IN_COURT)
                ? registrationDTO.getRegDate()
                : registrationDTO.getDeclinedDate();

        CourtAdmCaseMovement caseMovement = CourtAdmCaseMovement.builder()
                .caseId(registrationDTO.getCaseId())
                .claimId(registrationDTO.getClaimId())
                .statusId(registrationDTO.getStatus().getId())
                .statusTime(statusDate.atStartOfDay())
                .build();
        return save(caseMovement);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveWithNewTransaction(CourtAdmCaseMovement caseMovement) {
        courtAdmCaseMovementRepository.save(caseMovement);
    }

    @Override
    public List<CourtAdmCaseMovementResponseDTO> findAllByAdmCaseId(Long id) {
        return courtAdmCaseMovementRepository
                .findAllByCaseIdOrderByTimeDesc(id)
                .stream()
                .map(CourtAdmCaseMovementResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourtAdmCaseMovement> findAllByCaseAndClaimIds(Long caseId, Long claimId) {
        return courtAdmCaseMovementRepository.findAllByCaseIdAndClaimId(caseId, claimId);
    }

    @Override
    public boolean hasThirdMethod(Long caseId, Long claimId) {
        return !courtAdmCaseMovementRepository.findByCaseIdAndClaimIdAndStatusIdIn(
                caseId,
                claimId,
                CourtStatusAlias.getBaseOfThierdMethod(),
                PageUtils.one()
        ).isEmpty();
    }

    @Override
    public CourtAdmCaseMovementCountResponse findAdmCaseToCourtSentCount(Long caseId) {
        return courtAdmCaseMovementRepository.countByCaseId(caseId);
    }
}
