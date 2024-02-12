package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProperUbddDataBindValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProperUbddDataBind {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

