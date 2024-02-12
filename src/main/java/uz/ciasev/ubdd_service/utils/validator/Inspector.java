package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InspectorValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inspector {

    String message() default ErrorCode.USER_HAS_NO_ORGAN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
