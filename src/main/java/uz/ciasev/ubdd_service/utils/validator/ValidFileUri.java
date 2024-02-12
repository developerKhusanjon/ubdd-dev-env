package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.entity.dict.FileFormatAlias;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = FileUriValidator.class)
@Target({FIELD, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFileUri {

    String message() default ErrorCode.FILE_URI_INVALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    FileFormatAlias[] allow() default {};
}
