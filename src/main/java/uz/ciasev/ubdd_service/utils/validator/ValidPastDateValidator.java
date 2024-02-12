package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidPastDateValidator implements ConstraintValidator<ValidPastDate, LocalDate> {

    private ValidPastDate constraintAnnotation;

    @Override
    public void initialize(ValidPastDate constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }

        if (dateTooFarInPast(date)) {
            context
                    .buildConstraintViolationWithTemplate(constraintAnnotation.message())
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    private boolean dateTooFarInPast(LocalDate date) {
        return date.isBefore(LocalDate.now().minusDays(constraintAnnotation.minusDays()));
    }
}
