package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ArticlePartValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidArticlePart {

    String message() default ErrorCode.INVALID_ARTICLE_PART;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
