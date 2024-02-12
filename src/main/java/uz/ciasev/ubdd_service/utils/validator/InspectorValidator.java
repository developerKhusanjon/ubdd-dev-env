package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.entity.user.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class InspectorValidator implements ConstraintValidator<Inspector, User> {

    @Override
    public void initialize(Inspector constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        return Objects.nonNull(user) && Objects.nonNull(user.getOrgan());
    }
}
