package uz.ciasev.ubdd_service.service.publicapi.eventdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.CourtCaseChancelleryData;
import uz.ciasev.ubdd_service.entity.court.CourtCaseFields;
import uz.ciasev.ubdd_service.entity.resolution.*;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.*;
import uz.ciasev.ubdd_service.service.publicapi.dto.eventdata.*;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicApiWebhookEventCourtDataServiceImpl implements PublicApiWebhookEventCourtDataService {

    private final ViolatorService violatorService;
    private final AdmCaseService admCaseService;


    @Override
    public void setCourtDTOForMerge(PublicApiWebhookEventDataCourtDTO courtDTO, AdmCase mergedToCase) {
        List<PublicApiWebhookEventDataCourtMergedViolatorsDTO> violatorsDTOS = violatorService.findByAdmCaseId(mergedToCase.getId())
                .stream()
                .map(v -> {
                    PublicApiWebhookEventDataCourtMergedViolatorsDTO mergedViolatorsDTO = new PublicApiWebhookEventDataCourtMergedViolatorsDTO();
                    mergedViolatorsDTO.setViolatorId(v.getId());
                    mergedViolatorsDTO.setFirstNameLat(v.getPerson().getFirstNameLat());
                    mergedViolatorsDTO.setSecondNameLat(v.getPerson().getSecondNameLat());
                    mergedViolatorsDTO.setLastNameLat(v.getPerson().getLastNameLat());
                    mergedViolatorsDTO.setBirthDate(v.getPerson().getBirthDate());
                    return mergedViolatorsDTO;
                }).collect(Collectors.toList());

        courtDTO.setMergedToAdmCaseId(mergedToCase.getId());
        courtDTO.setMergedViolators(violatorsDTOS);
    }

    @Override
    public void setCourtDTOForSeparation(PublicApiWebhookEventDataCourtDTO courtDTO, AdmCase separatedCase) {
        PublicApiWebhookEventDataCourtSeparationDTO separationDTO = new PublicApiWebhookEventDataCourtSeparationDTO();

        List<PublicApiWebhookEventDataCourtSeparatedViolatorsDTO> violatorsDTOS = violatorService.findByAdmCaseId(separatedCase.getId())
                .stream()
                .map(v -> {
                    PublicApiWebhookEventDataCourtSeparatedViolatorsDTO mergedViolatorsDTO = new PublicApiWebhookEventDataCourtSeparatedViolatorsDTO();
                    mergedViolatorsDTO.setViolatorIdFrom(v.getSeparatedFromViolatorId());
                    mergedViolatorsDTO.setViolatorIdTo(v.getId());
                    return mergedViolatorsDTO;
                }).collect(Collectors.toList());

        separationDTO.setSeparatedToAdmCaseId(separatedCase.getId());
        separationDTO.setSeparationViolators(violatorsDTOS);

        if (courtDTO.getCaseSeparation() == null) {
            courtDTO.setCaseSeparation(new ArrayList<>());
        }

        courtDTO.getCaseSeparation().add(separationDTO);
    }

    @Override
    public void setCourtDTOForResolution(PublicApiWebhookEventDataCourtDTO courtDTO, Resolution resolution, List<Decision> decisions) {
//        List<PublicApiWebhookEventDataCourtDecisionDTO> decisionDTOS = decisionService.findByResolutionId(resolution.getId())
        List<PublicApiWebhookEventDataCourtDecisionDTO> decisionDTOS = decisions
                .stream()
                .map(d -> {
                    PublicApiWebhookEventDataCourtDecisionDTO violatorsDTO = new PublicApiWebhookEventDataCourtDecisionDTO();

                    violatorsDTO.setId(d.getViolatorId());
                    violatorsDTO.setDecisionId(d.getId());
                    violatorsDTO.setTerminationReasonId(d.getTerminationReasonId());

                    PublicApiWebhookEventDataCourtDecisionPunishmentDTO mainPunishment = new PublicApiWebhookEventDataCourtDecisionPunishmentDTO();
                    PublicApiWebhookEventDataCourtDecisionPunishmentDTO additionalPunishment = new PublicApiWebhookEventDataCourtDecisionPunishmentDTO();

                    Optional.ofNullable(d.getMainPunishment()).map(Punishment::getId).ifPresent(mainPunishment::setPunishmentId);
                    Optional.ofNullable(d.getMainPunishment()).map(Punishment::getPenalty).map(PenaltyPunishment::getAmount).ifPresent(mainPunishment::setPenaltyAmount);
                    Optional.ofNullable(d.getMainPunishment()).map(Punishment::getLicenseRevocation).map(l -> new PublicApiWebhookEventDataCourtDecisionDurationPunishmentDTO(l.getYears(), l.getMonths(), l.getDays())).ifPresent(mainPunishment::setLicenseRevocation);
                    Optional.ofNullable(d.getMainPunishment()).map(Punishment::getArrest).map(ArrestPunishment::getDays).ifPresent(mainPunishment::setArrestDays);
                    Optional.ofNullable(d.getMainPunishment()).map(Punishment::getConfiscation).map(ConfiscationPunishment::getAmount).ifPresent(mainPunishment::setConfiscationAmount);
                    Optional.ofNullable(d.getMainPunishment()).map(Punishment::getDeportation).map(DeportationPunishment::getDeportationDate).ifPresent(mainPunishment::setDeportationDate);
                    Optional.ofNullable(d.getMainPunishment()).map(Punishment::getDeportation).map(dep -> new PublicApiWebhookEventDataCourtDecisionDurationPunishmentDTO(dep.getYears(), dep.getMonths(), dep.getDays())).ifPresent(mainPunishment::setDeportation);
                    Optional.ofNullable(d.getMainPunishment()).map(Punishment::getWithdrawal).map(WithdrawalPunishment::getAmount).ifPresent(mainPunishment::setWithdrawalAmount);
                    Optional.ofNullable(d.getMainPunishment()).map(Punishment::getMedical).map(MedicalPunishment::getDays).ifPresent(mainPunishment::setMedicalDays);

                    Optional.ofNullable(d.getAdditionPunishment()).map(Punishment::getId).ifPresent(additionalPunishment::setPunishmentId);
                    Optional.ofNullable(d.getAdditionPunishment()).map(Punishment::getPenalty).map(PenaltyPunishment::getAmount).ifPresent(additionalPunishment::setPenaltyAmount);
                    Optional.ofNullable(d.getAdditionPunishment()).map(Punishment::getLicenseRevocation).map(l -> new PublicApiWebhookEventDataCourtDecisionDurationPunishmentDTO(l.getYears(), l.getMonths(), l.getDays())).ifPresent(additionalPunishment::setLicenseRevocation);
                    Optional.ofNullable(d.getAdditionPunishment()).map(Punishment::getArrest).map(ArrestPunishment::getDays).ifPresent(additionalPunishment::setArrestDays);
                    Optional.ofNullable(d.getAdditionPunishment()).map(Punishment::getConfiscation).map(ConfiscationPunishment::getAmount).ifPresent(additionalPunishment::setConfiscationAmount);
                    Optional.ofNullable(d.getAdditionPunishment()).map(Punishment::getDeportation).map(DeportationPunishment::getDeportationDate).ifPresent(additionalPunishment::setDeportationDate);
                    Optional.ofNullable(d.getAdditionPunishment()).map(Punishment::getDeportation).map(dep -> new PublicApiWebhookEventDataCourtDecisionDurationPunishmentDTO(dep.getYears(), dep.getMonths(), dep.getDays())).ifPresent(mainPunishment::setDeportation);
                    Optional.ofNullable(d.getAdditionPunishment()).map(Punishment::getWithdrawal).map(WithdrawalPunishment::getAmount).ifPresent(additionalPunishment::setWithdrawalAmount);
                    Optional.ofNullable(d.getAdditionPunishment()).map(Punishment::getMedical).map(MedicalPunishment::getDays).ifPresent(additionalPunishment::setMedicalDays);

                    violatorsDTO.setMainPunishment(mainPunishment);
                    violatorsDTO.setAdditionalPunishment(additionalPunishment);

                    return violatorsDTO;

                }).collect(Collectors.toList());

        courtDTO.setViolators(decisionDTOS);
    }

    @Override
    public void setCourtDTOForFields(PublicApiWebhookEventDataCourtDTO courtDTO, CourtCaseFields courtCaseFields) {
        courtDTO.setCaseId(courtCaseFields.getCaseId());
        courtDTO.setRegionId(courtCaseFields.getRegionId());
        courtDTO.setDistrictId(courtCaseFields.getDistrictId());
        courtDTO.setCourtStatusId(courtCaseFields.getStatus().getId());
        courtDTO.setCaseNumber(courtCaseFields.getCaseNumber());
        courtDTO.setHearingDate(courtCaseFields.getHearingDate());
        courtDTO.setReturnReasonId(courtCaseFields.getReturnReason());

        AdmCase admCase = admCaseService.getById(courtCaseFields.getCaseId());
        courtDTO.setCourtConsideringBasisId(admCase.getCourtConsideringBasisId());
    }

    @Override
    public void setCourtDTOForFields(PublicApiWebhookEventDataCourtDTO courtDTO, CourtCaseChancelleryData courtCaseFields) {
        courtDTO.setCaseId(courtCaseFields.getCaseId());
        courtDTO.setRegionId(courtCaseFields.getRegionId());
        courtDTO.setDistrictId(courtCaseFields.getDistrictId());
        courtDTO.setCourtStatusId(courtCaseFields.getStatusId());
        courtDTO.setDeclinedDate(courtCaseFields.getDeclinedDate());
        courtDTO.setDeclinedReasons(courtCaseFields.getDeclinedReasons());

        AdmCase admCase = admCaseService.getById(courtCaseFields.getCaseId());
        courtDTO.setCourtConsideringBasisId(admCase.getCourtConsideringBasisId());
    }
}
