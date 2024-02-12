package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankOrNullValidator implements ConstraintValidator<NotBlankOrNull, String> {

    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {

        if (str == null) {
            return true;
        }
        return str.trim().length() > 0;
    }
}
