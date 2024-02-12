package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameStrictureValidator.class)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameStructure {

    String message() default ErrorCode.USERNAME_STRUCTURE_INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
