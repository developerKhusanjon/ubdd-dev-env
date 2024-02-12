package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.utils.DateTimeUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class NotInFutureDateTimeValidator implements ConstraintValidator<NotInFuture, LocalDateTime> {

    @Override
    public void initialize(NotInFuture constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !DateTimeUtils.isInFuture(value);
    }
}
