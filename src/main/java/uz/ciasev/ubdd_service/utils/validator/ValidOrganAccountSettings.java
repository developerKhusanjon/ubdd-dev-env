package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidOrganAccountSettingsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrganAccountSettings {

    String message() default "INVALID_REQUEST";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
