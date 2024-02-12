package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotUzbCitizenValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotUzbCitizen {

    String message() default ErrorCode.MANUAL_DOCUMENT_CITIZENSHIP_MUST_NOT_BE_UZB;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
