package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxPageSizeValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxPageSize {

    int value();

    String message() default ErrorCode.PAGE_SIZE_TOO_LARGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
