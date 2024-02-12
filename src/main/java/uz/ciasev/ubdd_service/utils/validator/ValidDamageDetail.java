package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DamageDetailValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDamageDetail {

    String message() default ErrorCode.INVALID_DAMAGE_DETAIL;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

