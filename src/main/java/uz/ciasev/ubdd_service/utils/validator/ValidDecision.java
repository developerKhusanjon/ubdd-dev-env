package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {OrganDecisionValidator.class, CourtDecisionValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDecision {

    String message() default ErrorCode.DECISION_INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
