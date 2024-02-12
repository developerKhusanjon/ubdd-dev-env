package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidOrganValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrgan {

    String message() default "INVALID_ORGAN";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
