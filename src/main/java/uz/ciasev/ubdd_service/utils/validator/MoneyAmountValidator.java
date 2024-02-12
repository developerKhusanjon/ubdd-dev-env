package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MoneyAmountValidator implements ConstraintValidator<MoneyAmount, Long> {

    private MoneyAmount constraintAnnotation;

    @Override
    public void initialize(MoneyAmount constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Long amount, ConstraintValidatorContext context) {
        if (amount == null) {
            if (this.constraintAnnotation.required()) {
                context
                        .buildConstraintViolationWithTemplate(ErrorCode.AMOUNT_REQUIRED)
                        .addConstraintViolation();
                return false;
            } else {
                return true;
            }
        }

        if (amount == 0 && this.constraintAnnotation.allowZero()) {
            return true;
        }

        if (amount < 1) {
            context
                    .buildConstraintViolationWithTemplate(ErrorCode.AMOUNT_SIZE_SO_SMALL)
                    .addConstraintViolation();
            return false;
        }

        if (amount > 1000000000000000L - 1) {
            context
                    .buildConstraintViolationWithTemplate(ErrorCode.AMOUNT_SIZE_SO_LARGE)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
