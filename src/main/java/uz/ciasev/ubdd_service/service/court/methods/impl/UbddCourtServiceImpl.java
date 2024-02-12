package uz.ciasev.ubdd_service.service.court.methods.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.court.CourtAdmCaseMovement;
import uz.ciasev.ubdd_service.entity.court.CourtLog;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellationAlias;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ResolutionInAdmCaseAlreadyExists;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.exception.court.CourtResult;
import uz.ciasev.ubdd_service.exception.court.CourtValidationException;
import uz.ciasev.ubdd_service.exception.court.CourtWrappedException;
import uz.ciasev.ubdd_service.exception.court.ExternalException;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResultDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.UbddCourtRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtDefendantRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ThirdCourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.CheckCourtDuplicateRequestService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.eight.EighthMethodFromCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.five.FifthMethodFromCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.mapper.CourtRequestMapperFacade;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.CourtSecondMaterialMethodService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.CourtThreeMaterialMethodService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.nine.NineMethodFromCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.seven.SevenMethodFromCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.three.CourtFinalResultByInstanceAliases;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.three.ThirdMethodFromCourtService;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.two.SecondMethodFromCourtService;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.court.CourtAdmCaseMovementService;
import uz.ciasev.ubdd_service.service.court.CourtCaseFieldsService;
import uz.ciasev.ubdd_service.service.court.CourtLogService;
import uz.ciasev.ubdd_service.service.court.methods.CourtHandlingResponseService;
import uz.ciasev.ubdd_service.service.court.methods.UbddCourtService;
import uz.ciasev.ubdd_service.service.dict.court.CourtStatusDictionaryService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionActionService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;
import uz.ciasev.ubdd_service.service.victim.VictimService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import static uz.ciasev.ubdd_service.exception.ErrorCode.COURT_VALIDATION_ERROR;
import static uz.ciasev.ubdd_service.mvd_core.api.court.CourtMethod.COURT_THIRD;

@Slf4j
@Service
@RequiredArgsConstructor
public class UbddCourtServiceImpl implements UbddCourtService {

    private final AdmCaseService admCaseService;
    private final ResolutionService resolutionService;
    private final ResolutionActionService resolutionActionService;
    private final AdmCaseStatusService admStatusService;
    private final CourtStatusDictionaryService courtStatusDictionaryService;
    private final CourtLogService courtLogService;
    private final SecondMethodFromCourtService secondMethodFromCourtService;
    private final ThirdMethodFromCourtService thirdMethodFromCourtService;
    private final FifthMethodFromCourtService fifthMethodFromCourtService;
    private final SevenMethodFromCourtService sevenMethodFromCourtService;
    private final EighthMethodFromCourtService eighthMethodFromCourtService;
    private final NineMethodFromCourtService nineMethodFromCourtService;
    private final CourtSecondMaterialMethodService secondMaterialMethodFromCourtService;
    private final CourtThreeMaterialMethodService threeMaterialMethodService;
    private final CheckCourtDuplicateRequestService courtDuplicateRequestService;
    private final CourtRequestMapperFacade requestMapper;

    private final ViolatorService violatorService;
    private final VictimService victimService;


    @Override
    @Transactional(timeout = 60)
    public void sentCourt(UbddCourtRequest request) {

        var admCase = admCaseService.getById(request.getCaseId());

        admCase.setCourtOutNumber("SUD-" + admCase.getOrgan().getCode() + "-" + admCase.getNumber());
        admCase.setCourtOutDate(request.getCourtOutDate());
        admCase.setCourtRegion(request.getCourtRegion());
        admCase.setCourtDistrict(request.getCourtDistrict());
        admCase.setCourtConsideringBasis(request.getCourtConsideringBasis());
        admCase.setCourtConsideringAddition(request.getCourtConsideringAddition());
        admCase.setViolationPlaceType(request.getViolationPlaceType());
        admCase.setViolationPlaceAddress(request.getViolationPlaceAddress());

        admCase.setClaimId(request.getClaimId());

        admCase.setIs308(request.getIs308());

        admCase.setConsideredTime(null);

        admCase.setCourtStatus(courtStatusDictionaryService.getByAlias(CourtStatusAlias.SENT_TO_COURT));

        admStatusService.setStatus(admCase, AdmStatusAlias.SENT_TO_COURT);

        admCaseService.update(request.getCaseId(), admCase);

        if (request.getIs308()) {
            resolutionService.findActiveByAdmCaseId(request.getCaseId())
                    .ifPresent(resolution -> resolutionActionService.cancelResolutionByCourt(resolution, ReasonCancellationAlias.COURT_308_SENDING, null));
        }

    }


    @Override
    public CourtResponseDTO courtResolution(CourtRequestDTO<ThirdCourtResolutionRequestDTO> requestDTO) {

        replacePersonIdByViolatorId(requestDTO);

        CourtLog courtLog = courtLogService.save(requestDTO.getSendDocumentRequest(), COURT_THIRD);
        if (requestDTO.getSendDocumentRequest().isMaterial()) {
            ThirdCourtRequest mappedRequest = requestMapper.map(requestDTO.getSendDocumentRequest());
            threeMaterialMethodService.accept(mappedRequest);
        } else {

            try {
                thirdMethodFromCourtService.accept(requestDTO.getSendDocumentRequest());
            } catch (ResolutionInAdmCaseAlreadyExists e) {
                courtDuplicateRequestService.checkAndRemember(requestDTO.getSendDocumentRequest());
                throw e;
            } catch (ExternalException e) {
                e.setEnvelopeId(courtLog.getId());
                throw e;
            } catch (ApplicationException e) {
                throw new CourtWrappedException(e, courtLog.getId());
            }
        }
        return buildCourtResponse(courtLog.getId());
    }


    private void replacePersonIdByViolatorId(CourtRequestDTO<ThirdCourtResolutionRequestDTO> requestDTO) {

        Long caseId = requestDTO.getSendDocumentRequest().getCaseId();
        List<ThirdCourtDefendantRequestDTO> defendants = requestDTO.getSendDocumentRequest().getDefendant();

        if (defendants.stream().anyMatch(d -> d.getViolatorId() == null)) {
            if (defendants.size() != 1) {
                throw new CourtValidationException("One of defendants has null violatorId");
            }
        }

        Function<ThirdCourtDefendantRequestDTO, Long> violatorIdSupplier = defendants.size() == 1
                ? defendant -> violatorService.findSingleByAdmCaseId(caseId).getId()
                : defendant -> violatorService.findByAdmCaseIdAndPersonId(caseId, defendant.getViolatorId());

        defendants.forEach(defendant -> {

            defendant.setViolatorId(violatorIdSupplier.apply(defendant));

            defendant.getExactedDamage().forEach(damage -> {
                Long victimId = damage.getVictimId();
                if (victimId != null) {
                    damage.setVictimId(victimService.findByAdmCaseIdAndPersonId(caseId, victimId));
                }
            });
        });

        defendants.stream()
                .filter(defendant ->
                        defendant.getReturnReason() == null && requestDTO.getSendDocumentRequest().getStatus() == 17L &&
                                CourtFinalResultByInstanceAliases.getNameByValue(defendant.getFinalResult()).equals(CourtFinalResultByInstanceAliases.FR_I_CASE_RETURNING)
                )
                .forEach(defendant -> defendant.setReturnReason(99L));
    }

    private CourtResponseDTO buildCourtResponse(Long envelopeId) {
        return new CourtResponseDTO(
                new CourtResultDTO(CourtResult.SUCCESSFULLY, "Ok"),
                envelopeId
        );
    }


}
