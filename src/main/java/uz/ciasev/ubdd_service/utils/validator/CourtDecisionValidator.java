package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtDecisionRequestDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class CourtDecisionValidator extends DecisionValidator implements ConstraintValidator<ValidDecision, CourtDecisionRequestDTO> {


    @Override
    public boolean isValid(CourtDecisionRequestDTO decisionRequestDTO, ConstraintValidatorContext context) {
        if (decisionRequestDTO == null || decisionRequestDTO.getDecisionType() == null) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        return super.isValid(decisionRequestDTO, context);
    }
}
