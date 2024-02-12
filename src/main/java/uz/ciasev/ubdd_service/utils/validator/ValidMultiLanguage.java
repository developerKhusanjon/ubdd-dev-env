package uz.ciasev.ubdd_service.utils.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MultiLanguageNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMultiLanguage {

    String message() default "NAME_REQUIRED";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
