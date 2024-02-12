package uz.ciasev.ubdd_service.utils.validator;

import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxPageSizeValidator implements ConstraintValidator<MaxPageSize, Pageable> {

    private int maxSize;

    @Override
    public void initialize(MaxPageSize constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        maxSize = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Pageable value, ConstraintValidatorContext context) {
        if (value.isUnpaged()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.PAGE_SIZE_REQUIRED).addConstraintViolation();
            return false;
        }

        if (value.getPageSize() > maxSize) {
            return false;
        }

        return true;
    }
}
