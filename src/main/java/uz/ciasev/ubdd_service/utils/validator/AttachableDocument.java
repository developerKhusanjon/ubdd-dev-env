package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AttachableDocumentValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AttachableDocument {

    String message() default ErrorCode.DOCUMENT_TYPE_IS_NOT_ATTACHABLE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

