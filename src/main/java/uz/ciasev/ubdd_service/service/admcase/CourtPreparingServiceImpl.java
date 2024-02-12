package uz.ciasev.ubdd_service.service.admcase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.admcase.AdmCaseCourtDeclarationRequestDTO;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationRepeatabilityStatus;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationCollectingError;
import uz.ciasev.ubdd_service.service.dict.court.ViolationRepeatabilityStatusDictionaryService;
import uz.ciasev.ubdd_service.service.protocol.ProtocolService;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;
import uz.ciasev.ubdd_service.service.validation.AdmCaseValidationService;
import uz.ciasev.ubdd_service.service.validation.ValidationService;
import uz.ciasev.ubdd_service.service.violator.ViolatorService;

import static uz.ciasev.ubdd_service.entity.action.ActionAlias.EDIT_ADM_CASE_COURT_DECLARATION;
import static uz.ciasev.ubdd_service.entity.action.ActionAlias.START_PREPARE_ADM_CASE_TO_COURT;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.PREPARE_FOR_COURT;

@Service
@RequiredArgsConstructor
public class CourtPreparingServiceImpl implements CourtPreparingService {

    private final AdmCaseService admCaseService;
    private final AdmCaseAccessService admCaseAccessService;
    private final AdmCaseStatusService admStatusService;
    private final AdmCaseValidationService admCaseValidationService;
    private final ProtocolService protocolService;
    private final ValidationService validationService;
    private final ViolatorService violatorService;
    private final ViolationRepeatabilityStatusDictionaryService violationRepeatabilityStatusService;

    @Override
    public void startPreparing(User user, Long admCaseId) {

        AdmCase admCase = admCaseService.getById(admCaseId);

        admCaseAccessService.checkConsiderActionWithAdmCase(user, START_PREPARE_ADM_CASE_TO_COURT, admCase);

        admStatusService.setStatus(admCase, PREPARE_FOR_COURT);

        admCaseService.update(admCaseId, admCase);

    }

    @Override
    public void updateCourtDeclaration(User user, Long admCaseId, AdmCaseCourtDeclarationRequestDTO requestDTO) {
        AdmCase admCase = admCaseService.getById(admCaseId);
        admCase.setCourtOutNumber("SUD-" + admCase.getOrgan().getCode() + "-" + admCase.getNumber());
        admCaseAccessService.checkConsiderActionWithAdmCase(user, EDIT_ADM_CASE_COURT_DECLARATION, admCase);

        admCaseValidationService.validateCourtDeclaration(user, admCase, requestDTO);

        admCaseService.update(admCaseId, requestDTO.applyTo(admCase));
    }

    @Override
    public void validateCourtSend(User user, Long admCaseId, ActionAlias actionAlias) {
        AdmCase admCase = admCaseService.getById(admCaseId);
        admCaseAccessService.checkConsiderActionWithAdmCase(user, actionAlias, admCase);

        ValidationCollectingError error = new ValidationCollectingError();

        error.addIf(admCase.getCourtRegionId() == null, ErrorCode.COURT_FIELDS_REQUIRED);

        violatorService.findByAdmCaseId(admCaseId)
                .forEach(v -> {
//                    error.addIf(v.getDependentAmount() == null, ErrorCode.ONE_FROM_VIOLATORS_HAS_NO_DEPENDENT_AMOUNT);
                    error.addIf(v.getHealthStatusId() == null, ErrorCode.ONE_FROM_VIOLATORS_HAS_NO_HEALF_STAUS);
                    error.addIf(v.getEducationLevelId() == null, ErrorCode.ONE_FROM_VIOLATORS_HAS_NO_EDUCATION_LEVEL);
                    error.addIf(v.getMaritalStatusId() == null, ErrorCode.ONE_FROM_VIOLATORS_HAS_NO_MARITAL_STATUS);
                    error.addIf(v.getMaritalStatusId() == null, ErrorCode.ONE_FROM_VIOLATORS_HAS_NO_MARITAL_STATUS);
                    error.addIf(v.getViolationRepeatabilityStatusId() == null, ErrorCode.ONE_FROM_VIOLATORS_HAS_NO_VIOLATION_REPEATABILITY_STATUS);

                    if (v.getViolationRepeatabilityStatusId() != null) {
                        ViolationRepeatabilityStatus violationRepeatabilityStatus = violationRepeatabilityStatusService.getById(v.getViolationRepeatabilityStatusId());
                        if (violationRepeatabilityStatus.isNeedEarlierViolatedArticleParts()) {
                            if (v.getEarlierViolatedArticleParts().isEmpty()) {
                                error.add(ErrorCode.EARLIER_VIOLATED_ARTICLE_PARTS_REQUIRED);
                            }
                        } else {
                            if (!v.getEarlierViolatedArticleParts().isEmpty()) {
                                error.add(ErrorCode.EARLIER_VIOLATED_ARTICLE_PARTS_MAST_BE_EMPTY);
                            }
                        }
                    }
                });

        error.throwErrorIfNotEmpty();
    }
}
