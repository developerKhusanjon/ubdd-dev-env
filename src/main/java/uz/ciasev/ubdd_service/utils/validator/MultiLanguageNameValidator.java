package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiLanguageNameValidator implements ConstraintValidator<ValidMultiLanguage, MultiLanguage> {

    @Override
    public void initialize(ValidMultiLanguage constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultiLanguage multiLanguage, ConstraintValidatorContext context) {

        if (multiLanguage == null) {

            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("RU_CANT_BE_EMPTY")
                    .addConstraintViolation()
                    .buildConstraintViolationWithTemplate("KIR_CANT_BE_EMPTY")
                    .addConstraintViolation()
                    .buildConstraintViolationWithTemplate("LAT_CANT_BE_EMPTY")
                    .addConstraintViolation();

            return false;
        }
        return true;
    }
}
