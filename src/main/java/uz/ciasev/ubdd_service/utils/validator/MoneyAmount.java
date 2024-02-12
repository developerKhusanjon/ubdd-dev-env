package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MoneyAmountValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MoneyAmount {

    String message();

    boolean required() default true;

    boolean allowZero() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
