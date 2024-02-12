package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidArrestOutInformationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidArrestOutInformation {

    String message() default ErrorCode.OUT_DATE_AND_OUT_STATE_MUST_EDITED_BOTH;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
