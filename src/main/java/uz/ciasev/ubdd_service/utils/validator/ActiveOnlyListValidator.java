package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.entity.dict.AbstractDict;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ActiveOnlyListValidator implements ConstraintValidator<ActiveOnlyList, List> {

    @Override
    public void initialize(ActiveOnlyList constraintAnnotation) {
    }

    @Override
    public boolean isValid(List entityList, ConstraintValidatorContext context) {
        if (entityList == null) {
            return true;
        }
        if (entityList.stream().anyMatch(o -> !(o instanceof AbstractDict))) {
            return false;
        }
        return entityList.stream().allMatch(o -> ((AbstractDict)o).getIsActive());
    }
}
