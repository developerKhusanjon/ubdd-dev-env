package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValueOrAllTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidValueOrAllType {

    String message() default ErrorCode.VALUE_OR_ALL_TYPE_INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
