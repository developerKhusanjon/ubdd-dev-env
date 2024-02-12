package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EvidenceDataValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEvidenceData {

    String message() default ErrorCode.EVIDENCE_VALIDATION_ERROR;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
