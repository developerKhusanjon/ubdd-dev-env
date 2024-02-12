package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.entity.dict.AbstractDict;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ActiveOnlyValidator implements ConstraintValidator<ActiveOnly, AbstractDict> {

    @Override
    public void initialize(ActiveOnly constraintAnnotation) {
    }

    @Override
    public boolean isValid(AbstractDict entity, ConstraintValidatorContext context) {
        if (entity == null) {
            return true;
        }
        return entity.getIsActive();
    }
}
