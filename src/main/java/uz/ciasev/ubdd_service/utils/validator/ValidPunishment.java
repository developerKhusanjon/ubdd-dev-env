package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {CourtPunishmentValidator.class, OrganPunishmentValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPunishment {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
