package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.organ.SingleResolutionRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.article.ArticleParticipantTypeDictionaryService;
import uz.ciasev.ubdd_service.service.validation.ProtocolValidationService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias.PUNISHMENT;
import static uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias.TERMINATION;

@RequiredArgsConstructor
public class OrganDecisionValidator extends DecisionValidator implements ConstraintValidator<ValidDecision, SingleResolutionRequestDTO> {

    private final ArticleParticipantTypeDictionaryService articleParticipantTypeService;
    private final ProtocolValidationService protocolValidationService;


    @Override
    public boolean isValid(SingleResolutionRequestDTO decisionRequestDTO, ConstraintValidatorContext context) {
        if (decisionRequestDTO == null || decisionRequestDTO.getDecisionType() == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        boolean isValid = super.isValid(decisionRequestDTO, context);

        if (decisionRequestDTO.getExecutionFromDate() != null) {

            if (LocalDate.now().isAfter(decisionRequestDTO.getExecutionFromDate())) {
                isValid = false;
                context.buildConstraintViolationWithTemplate(ErrorCode.EXECUTION_FROM_DATE_IN_PAST).addConstraintViolation();
            }

            if (decisionRequestDTO.getExecutionFromDate().isAfter(LocalDate.now().plusMonths(1))) {
                isValid = false;
                context.buildConstraintViolationWithTemplate(ErrorCode.EXECUTION_FROM_DATE_MORE_THEN_1_MONTH_IN_FUTURE).addConstraintViolation();
            }
        }

        if (decisionRequestDTO.getDecisionType().is(PUNISHMENT) && Objects.isNull(decisionRequestDTO.getArticlePart())) {
            isValid = false;
            context.buildConstraintViolationWithTemplate(ErrorCode.ARTICLE_PART_REQUIRED).addConstraintViolation();
        }

        if (decisionRequestDTO.getDecisionType().is(PUNISHMENT) && Objects.isNull(decisionRequestDTO.getIsJuridic())) {
            isValid = false;
            context.buildConstraintViolationWithTemplate(ErrorCode.IS_JURIDIC_REQUIRED).addConstraintViolation();
        }

        Optional<String> error = protocolValidationService.checkArticleWithParticipantType(decisionRequestDTO.getArticlePart(), decisionRequestDTO.getIsJuridic());
        if (error.isPresent()) {
            isValid = false;
            context.buildConstraintViolationWithTemplate(error.get()).addConstraintViolation();
        }

        Optional<String> violationTypeError = protocolValidationService.validateArticleViolationTypePresence(decisionRequestDTO);
        if (violationTypeError.isPresent()) {
            isValid = false;
            context.buildConstraintViolationWithTemplate(violationTypeError.get()).addConstraintViolation();
        }

        if (decisionRequestDTO.getDecisionType().is(TERMINATION) && !decisionRequestDTO.getCompensations().isEmpty()) {
            isValid = false;
            context.buildConstraintViolationWithTemplate(ErrorCode.COMPENSATION_IN_TERMINATION_DECISION_NOT_ALLOW).addConstraintViolation();
        }

        return isValid;
    }
}
