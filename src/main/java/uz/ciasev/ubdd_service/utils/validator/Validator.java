package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.ConstraintValidatorContext;

@FunctionalInterface
public interface Validator<T> {

    boolean isValid(T object, ConstraintValidatorContext context);
}
