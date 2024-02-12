package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.request.ValueOrAllType;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValueOrAllTypeValidator implements ConstraintValidator<ValidValueOrAllType, ValueOrAllType<?>> {

    @Override
    public void initialize(ValidValueOrAllType constraintAnnotation) {
    }

    @Override
    public boolean isValid(ValueOrAllType entity, ConstraintValidatorContext context) {
        if (entity == null) {
            return true;
        }

        if (entity.getIsAll() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.IS_ALL_REQUIRED).addConstraintViolation();
            return false;
        }

        if (entity.getIsAll()) {
            if (entity.isValuePresent()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorCode.VALUE_NOT_ACCEPT_FOR_IS_ALL_TRUE).addConstraintViolation();
                return false;
            }
        } else {
            if (entity.isValueEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(ErrorCode.VALUE_REQUIRED_FOR_IS_ALL_FALSE).addConstraintViolation();
                return false;
            }
        }

        return true;
    }

}
