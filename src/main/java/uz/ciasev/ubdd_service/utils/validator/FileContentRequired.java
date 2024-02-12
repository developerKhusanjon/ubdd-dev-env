package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileContentRequiredValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileContentRequired {

    String message() default ErrorCode.REQUIRED_PARAMS_MISSING;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
