package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.violator.ViolatorRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class ValidViolatorValidator implements ConstraintValidator<ValidViolator, ViolatorRequestDTO> {

    @Override
    public boolean isValid(ViolatorRequestDTO violatorRequestDTO, ConstraintValidatorContext context) {

        if (violatorRequestDTO == null) {
            return true;
        }

        boolean isValid = true;

        context.disableDefaultConstraintViolation();

        if (violatorRequestDTO.getViolationRepeatabilityStatus() != null) {
            if (violatorRequestDTO.getViolationRepeatabilityStatus().isNeedEarlierViolatedArticleParts()) {
                if (violatorRequestDTO.getEarlierViolatedArticleParts().isEmpty()) {
                    context.buildConstraintViolationWithTemplate(ErrorCode.EARLIER_VIOLATED_ARTICLE_PARTS_REQUIRED).addConstraintViolation();
                    isValid = false;
                }
            } else {
                if (!violatorRequestDTO.getEarlierViolatedArticleParts().isEmpty()) {
                    context.buildConstraintViolationWithTemplate(ErrorCode.EARLIER_VIOLATED_ARTICLE_PARTS_MAST_BE_EMPTY).addConstraintViolation();
                    isValid = false;
                }
            }
        }

        return isValid;
    }
}
