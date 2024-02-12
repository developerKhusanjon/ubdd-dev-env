package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidFutureDateValidator implements ConstraintValidator<ValidFutureDate, LocalDate> {

    private ValidFutureDate constraintAnnotation;

    @Override
    public void initialize(ValidFutureDate constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }

        if (dateTooFarInFuture(date)) {
            context
                    .buildConstraintViolationWithTemplate(constraintAnnotation.message())
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean dateTooFarInFuture(LocalDate date) {
        return date.isAfter(LocalDate.now().plusDays(constraintAnnotation.plusDays()));
    }
}
