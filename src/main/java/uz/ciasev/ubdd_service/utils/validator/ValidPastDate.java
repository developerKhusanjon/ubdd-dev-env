package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPastDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPastDate {

    String message();

    long minusDays() default 0L;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

