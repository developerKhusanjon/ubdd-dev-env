package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidFutureDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFutureDate {

    String message();

    long plusDays() default 0L;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

