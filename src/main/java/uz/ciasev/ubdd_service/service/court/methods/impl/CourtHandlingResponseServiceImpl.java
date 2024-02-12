package uz.ciasev.ubdd_service.service.court.methods;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.CourtResponseDTO;
import uz.ciasev.ubdd_service.entity.court.CourtAdmCaseMovement;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellationAlias;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.exception.ValidationException;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.court.CourtAdmCaseMovementService;
import uz.ciasev.ubdd_service.service.court.CourtCaseFieldsService;
import uz.ciasev.ubdd_service.service.dict.court.CourtStatusDictionaryService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionActionService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;

import java.time.LocalDateTime;

import static uz.ciasev.ubdd_service.exception.ErrorCode.COURT_VALIDATION_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourtHandlingResponseServiceImpl implements CourtHandlingResponseService {

    private final AdmCaseService admCaseService;
    private final ResolutionService resolutionService;
    private final ResolutionActionService resolutionActionService;
    private final AdmCaseStatusService admStatusService;
    private final CourtAdmCaseMovementService courtAdmCaseMovementService;
    private final CourtCaseFieldsService courtCaseFieldsService;
    private final CourtStatusDictionaryService courtStatusDictionaryService;

    @Override
    @Transactional(timeout = 60)
    public void handleResponse(CourtResponseDTO response, Long caseId, boolean is308, boolean isSentToCourt) {
        var admCase = admCaseService.getById(caseId);

        if (response != null && response.getResult() != null) {
            var result = response.getResult();

            CourtAdmCaseMovement movement;

            if (result.isSuccessfully()) {
                admCase.setClaimId(response.getEnvelopeId());
                admCase.setIs308(is308);
                admCase.setConsideredTime(null);
                admCase.setCourtStatus(courtStatusDictionaryService.getByAlias(CourtStatusAlias.SENT_TO_COURT));
                admStatusService.setStatus(admCase, AdmStatusAlias.SENT_TO_COURT);


                movement = buildCourtCaseMovement(caseId, response.getEnvelopeId(), null, isSentToCourt);
                courtAdmCaseMovementService.save(movement);

                admCaseService.update(caseId, admCase);
                courtCaseFieldsService.handleSend(admCase, response.getEnvelopeId());

                if (is308) {
                    resolutionService.findActiveByAdmCaseId(caseId)
                            .ifPresent(resolution -> resolutionActionService.cancelResolutionByCourt(resolution, ReasonCancellationAlias.COURT_308_SENDING, null));
                }


            } else {
                // todo notify user about problem
                movement = buildCourtCaseMovement(caseId, null, result.getResultDescription(), false);
                courtAdmCaseMovementService.saveWithNewTransaction(movement);

                throw new ValidationException(COURT_VALIDATION_ERROR);
            }
        }
    }

    private CourtAdmCaseMovement buildCourtCaseMovement(Long caseId, Long claimId, String errors, Boolean isSentToCourt) {
        return CourtAdmCaseMovement.builder()
                .caseId(caseId)
                .claimId(claimId)
                .statusTime(LocalDateTime.now())
                .validationErrors(errors)
                .isSentToCourt(isSentToCourt)
                .build();
    }
}
