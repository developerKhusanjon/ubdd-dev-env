package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default ErrorCode.PASSWORD_MUST_BE_AT_LEAST_SIX_SYMBOLS_LONG;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
