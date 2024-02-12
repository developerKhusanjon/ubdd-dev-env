package uz.ciasev.ubdd_service.utils.validator;

import lombok.Getter;

import javax.validation.ConstraintValidatorContext;

@Getter
public class ValidationHelper {

     private final ConstraintValidatorContext context;
     private boolean isValid;


    public ValidationHelper(ConstraintValidatorContext context) {
        this.context = context;
        this.isValid = true;
    }

    public void accept(String error) {
        isValid = false;
        context.buildConstraintViolationWithTemplate(error).addConstraintViolation();
    }
}
