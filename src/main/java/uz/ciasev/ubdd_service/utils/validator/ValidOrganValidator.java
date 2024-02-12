package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.dict.request.OrganRequestDTO;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidOrganValidator implements ConstraintValidator<ValidOrgan, OrganRequestDTO> {

    @Override
    public void initialize(ValidOrgan constraintAnnotation) {
    }

    @Override
    public boolean isValid(OrganRequestDTO entity, ConstraintValidatorContext context) {
        if (entity == null) {
            return true;
        }

        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        if (entity.getAllowSmsNotification() && entity.getSmsContract() == null) {
            context.buildConstraintViolationWithTemplate(ErrorCode.SMS_CONTRACT_REQUIRED_FOR_ALLOW_SMS_NOTIFICATION).addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
