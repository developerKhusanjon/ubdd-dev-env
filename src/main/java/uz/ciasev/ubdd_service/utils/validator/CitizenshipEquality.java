package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CitizenshipEqualityValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CitizenshipEquality {

    String message() default ErrorCode.CITIZENSHIP_BY_GIVEN_DOC_AND_IN_FORM_MUST_BE_EQUALS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
