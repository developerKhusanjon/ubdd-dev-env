package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.utils.DateTimeUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class NotInFutureDateValidator implements ConstraintValidator<NotInFuture, LocalDate> {

    @Override
    public void initialize(NotInFuture constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !DateTimeUtils.isInFuture(value);
    }
}
