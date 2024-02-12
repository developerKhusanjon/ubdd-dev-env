package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {NotInFutureDateValidator.class, NotInFutureDateTimeValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotInFuture {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
