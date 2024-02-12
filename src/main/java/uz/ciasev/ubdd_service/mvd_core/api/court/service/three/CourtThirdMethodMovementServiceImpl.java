package uz.ciasev.ubdd_service.mvd_core.api.court.service.three;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtCaseMovementRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtDefendantRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.court.CourtViolatorNotFoundException;
import uz.ciasev.ubdd_service.exception.implementation.ImplementationException;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.court.CourtAdmCaseMovementService;
import uz.ciasev.ubdd_service.service.court.trans.CourtTransferDictionaryService;
import uz.ciasev.ubdd_service.service.court.trans.CourtTransGeographyService;
import uz.ciasev.ubdd_service.service.main.migration.MainService;
import uz.ciasev.ubdd_service.service.main.resolution.CourtResolutionMainService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.mvd_core.api.court.service.three.CourtFinalResultByInstanceAliases.*;
import static uz.ciasev.ubdd_service.mvd_core.api.court.service.three.CourtFinalResultByInstanceAliases.getNameByValue;
import static uz.ciasev.ubdd_service.entity.court.CourtMovementAlias.*;

@Service
@RequiredArgsConstructor
public class CourtThirdMethodMovementServiceImpl implements CourtThirdMethodMovementService {

    public static final Long FINAL_RESULT_CODE_113 = 113L;

    private final MainService mainService;
    private final AdmCaseService admCaseService;
    private final ViolatorService violatorService;
    private final CourtTransferDictionaryService courtTransferService;
    private final CourtTransGeographyService courtTransGeographyService;
    private final CourtAdmCaseMovementService courtAdmCaseMovementService;
    private final CourtResolutionMainService resolutionMainService;

    /**
     * Движение "Регистрация I"
     */
    @Override
    public void makeRegistration(ThirdCourtResolutionRequestDTO resolution) {
        Long caseId = resolution.getCaseId();
        Long claimId = resolution.getClaimId();
        updateAdmCase(caseId, claimId, resolution.getCourt(), claimId);
//        updateViolators(resolution);
        courtAdmCaseMovementService.save(resolution, claimId, REGISTRATION);
    }

    /**
     * Движение "Пересмотр III"
     */
    @Override
    public void makeRevision(ThirdCourtResolutionRequestDTO resolution) {
        var caseMovement = resolution.getCaseMovement();
        var caseId = resolution.getCaseId();
        var oldClaimId = caseMovement.getClaimReviewId();
        var newClaimId = resolution.getClaimId();

        updateAdmCase(caseId, oldClaimId, resolution.getCourt(), newClaimId);
//        updateViolators(resolution);
        courtAdmCaseMovementService.save(resolution, newClaimId, REVISION);

        resolutionMainService.revisionCaseInCourt(caseId, newClaimId);
//        resolutionMainService.courtCancelResolutionByAdmCaseAndSetNewAdmStatus(caseId, newClaimId, SENT_TO_COURT, true, false);
    }

    /**
     * Движение "Разъединение IV"
     * Мы выделяем нарушителей в новое дело и сохраняем все поля регистрации в нем, кроме caseNumber
     */
    @Override
    public void makeSeparation(ThirdCourtResolutionRequestDTO resolution) {
        var caseId = resolution.getCaseId();
        var caseMovement = resolution.getCaseMovement();

        for (var separation : caseMovement.getCaseSeparation()) {
            var claimId = separation.getClaimSeparationId();

            List<Long> violatorIds = separation.getCaseSeparationViolatorId()
                    .stream()
                    .map(personId -> violatorService.findByAdmCaseIdAndPersonId(caseId, personId))
                    .collect(Collectors.toList());

            var newAdmCase = mainService.courtSeparateAdmCase(caseId, violatorIds, claimId);

            updateAdmCase(newAdmCase.getId(), claimId, resolution.getCourt(), claimId);
            courtAdmCaseMovementService.save(resolution, claimId, SEPARATION);
        }
//        updateViolators(resolution);
    }

    /**
     * Движение "Передача II"
     */
    @Override
    public void makeTransfer(ThirdCourtResolutionRequestDTO resolution) {
        var caseMovement = resolution.getCaseMovement();
        var caseId = resolution.getCaseId();
        var oldClaimId = resolution.getClaimId();
        var newClaimId = caseMovement.getOtherCourtClaimId();

        updateAdmCase(caseId, oldClaimId, caseMovement.getOtherCourtId(), newClaimId);
//        updateViolators(resolution);
        courtAdmCaseMovementService.save(resolution, newClaimId, TRANSFER);
    }

    /**
     * Движение "Объединение VI"
     */
    @Override
    public void makeMerge(ThirdCourtResolutionRequestDTO resolution) {
        var caseId = resolution.getCaseId();
        var claimMergeId = resolution.getCaseMovement().getClaimMergeId();
        var admCaseMerge = admCaseService.getByClaimId(claimMergeId);

        mainService.courtMergeAdmCases(caseId, admCaseMerge.getId());
        courtAdmCaseMovementService.save(resolution, claimMergeId, MERGE);
    }

    /**
     * Проверить есть ли пересмотр, если есть, то провалидировать обязательные поля,
     * за это отвечает поле caseReviewId
     */
    @Override
    public boolean hasRevision(ThirdCourtCaseMovementRequestDTO movements) {
        return (movements != null && movements.getClaimReviewId() != null);
    }

    /**
     * Проверить есть ли разъединение, если есть, то провалидировать обязательные поля,
     * за это отвечает блок caseSeparation
     */
    @Override
    public boolean hasSeparation(ThirdCourtCaseMovementRequestDTO movements) {
        return (movements != null && movements.getCaseSeparation() != null);
    }

    /**
     * Проверить есть ли передача, если есть, то провалидировать обязательные поля,
     * за это отвечает поле otherCourtClaimId
     */
    @Override
    public boolean hasTransfer(ThirdCourtCaseMovementRequestDTO movements) {
        return (movements != null && movements.getOtherCourtClaimId() != null);
    }

    /**
     * Проверить есть ли объединение, если есть, то провалидировать обязательные поля,
     * за это отвечает поле caseMergeId
     */
    @Override
    public boolean hasMerge(ThirdCourtCaseMovementRequestDTO movements) {
        return movements != null && movements.getClaimMergeId() != null;
    }

    @Override
    public boolean hasDuplicateMovements(ThirdCourtResolutionRequestDTO resolution) {
        boolean hasDuplicates = false;
        Long caseId = resolution.getCaseId();
        Long claimReviewId = null;
        Long claimTransferId = null;

        boolean hasRevision = hasRevision(resolution.getCaseMovement());
        if (hasRevision)
            claimReviewId = resolution.getClaimId();

        boolean hasTransfer = hasTransfer(resolution.getCaseMovement());
        if (hasTransfer)
            claimTransferId = resolution.getCaseMovement().getOtherCourtClaimId();

        if (claimReviewId != null) {
            var movements = courtAdmCaseMovementService
                    .findAllByCaseAndClaimIds(caseId, claimReviewId);

            if (!movements.isEmpty())
                hasDuplicates = true;
        }

        if (claimTransferId != null) {
            var movements = courtAdmCaseMovementService
                    .findAllByCaseAndClaimIds(caseId, claimTransferId);

            if (!movements.isEmpty())
                hasDuplicates = true;
        }
        return hasDuplicates;
    }

    private void updateAdmCase(Long caseId,
                               Long oldClaimId,
                               Long court,
                               Long newClaimId) {

        if (newClaimId == null) {
            throw new ImplementationException("New claimId in updateAdmCase is null!");
        }

        AdmCase admCase = admCaseService.getByIdAndClaimId(caseId, oldClaimId);

        admCase.setClaimId(newClaimId);

        CourtTransfer courtTransfer = courtTransferService.findByExternalId(court);
        admCase.setCourtRegion(courtTransfer.getRegion());
        admCase.setCourtDistrict(courtTransfer.getDistrict());


//        hasRevision - статус дела меняеться при обработке пересмотра
//        isSendingToCourt - вообще очень странная функция, она не должна никогда вернуть истину, потаму что у суда нет этого статауса
//        boolean isSendingToCourt = hasDecisionSendingToOtherCourt(resolution.getDefendant(), courtStatus);
//        if (isSendingToCourt || hasRevision) {
//            admStatusService.setStatus(admCase, AdmStatusAlias.SENT_TO_COURT);
//        }

//        if (courtStatus.getAlias().equals(RETURNED)) {
//            admStatusService.setStatus(admCase, AdmStatusAlias.RETURN_FROM_COURT);
//        }
        admCaseService.update(admCase.getId(), admCase);
    }

//    @Override
//    public AdmCase updateAdmStatus(Long caseId) {
//        AdmCase admCase = admCaseService.getDTOById(caseId);
//        admStatusService.setStatus(admCase, AdmStatusAlias.RETURN_FROM_COURT);
//        return admCaseService.update(admCase.getId(), admCase);
//    }

    private boolean hasDecisionSendingToOtherCourt(List<ThirdCourtDefendantRequestDTO> defendants, CourtStatus courtStatus) {
        return defendants != null
                && courtStatus.getAlias().equals(CourtStatusAlias.SENT_TO_COURT)
                && defendants.get(0).getFinalResult().equals(FINAL_RESULT_CODE_113);
    }

    @Override
    public void updateViolators(ThirdCourtResolutionRequestDTO resolution) {
        var defendants = resolution.getDefendant();

        if (defendants != null) {
            var violatorIds = calculateViolatorAndRemoveSeparationIds(resolution);
            var violators = violatorService.findViolatorsByIds(violatorIds);

            if (violators.size() != violatorIds.size()) {
                for (var violator : violators)
                    violatorIds.remove(violator.getId());
                throw new CourtViolatorNotFoundException(violatorIds);
            }

            Map<Long, ThirdCourtDefendantRequestDTO> map = defendants
                    .stream()
                    .collect(Collectors
                            .toMap(ThirdCourtDefendantRequestDTO::getViolatorId, v -> v));

            for (Violator violator : violators) {
                var defendant = map.get(violator.getId());
                if (defendant != null) {
                    violator.setDefendantId(defendant.getDefendantId());
                    var returnReasonId = defendant.getReturnReason();

                    if (defendant.getFinalResult() != null) {
                        if (returnReasonId == null && FR_I_CASE_RETURNING.equals(getNameByValue(defendant.getFinalResult()))) {
                            returnReasonId = 99L;
                        }
                    }

                    violator.setCourtReturnReasonId(returnReasonId);
                    violator.setIsParticipated(defendant.getIsParticipated());
                    violator.setProsecutorRegion(calcProsecutorRegion(defendant));
                    // todo УВЕДОМИТЬ ПРОКУРОРА ОБ УГОЛОВНОМ ДЕЛЕ
                }
            }
            violatorService.saveAll(violators);
        }

    }

    private List<Long> calculateViolatorAndRemoveSeparationIds(ThirdCourtResolutionRequestDTO resolution) {
        var defendants = resolution.getDefendant();

        var violatorIds = defendants
                .stream()
                .map(ThirdCourtDefendantRequestDTO::getViolatorId)
                .collect(Collectors.toList());

        var movements = resolution.getCaseMovement();
        if (movements != null && movements.getCaseSeparation() != null) {
            var separation = movements.getCaseSeparation();

            for (var sep : separation) {
                violatorIds.removeAll(sep.getCaseSeparationViolatorId());
            }
        }

        return violatorIds;
    }

//    private void saveCaseCourtMovement(ThirdCourtResolutionRequestDTO resolution,
//                                       Long claimId,
//                                       CourtMovementAlias alias) {
//        var caseId = resolution.getCaseId();
//
//        var caseMovement = new CourtAdmCaseMovement();
//        caseMovement.setCaseId(caseId);
//        caseMovement.setClaimId(claimId);
//        caseMovement.setStatusId(resolution.getStatus());
//        caseMovement.setStatusTime(resolution.getHearingDate());
//        caseMovement.setAlias(alias);
//
//        courtAdmCaseMovementService.save(caseMovement);
//    }

    private Region calcProsecutorRegion(ThirdCourtDefendantRequestDTO defendant) {
        Long reasonId = defendant.getReturnReason();
        Long districtId = defendant.getProsecutorRegionId();

        if (reasonId != null && reasonId == 1L && districtId != null) {
            return courtTransGeographyService.getCourtDistrictByExternalId(defendant.getProsecutorRegionId()).getRegion();
        }

        return null;
    }

//    private Optional<CourtTransGeography> calcProsecutorRegion(ThirdCourtDefendantRequestDTO defendant) {
//        CourtTransGeography courtGeography = null;
//        Long reasonId = defendant.getReturnReason();
//        Long districtId = defendant.getProsecutorRegionId();
//
//        if (reasonId != null && reasonId == 1L && districtId != null)
//            courtGeography = courtTransGeographyService
//                    .findGeographyByExternalDistrict(districtId)
//                    .orElseThrow(() -> new CourtValidationException(PROSECUTOR_REGION_NOT_FOUND));
//
//        return Optional.ofNullable(courtGeography);
//    }
}
