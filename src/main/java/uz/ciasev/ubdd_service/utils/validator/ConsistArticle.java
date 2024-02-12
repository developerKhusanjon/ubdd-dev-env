package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConsistArticleValidator.class)
@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsistArticle {

    String message() default ErrorCode.ARTICLE_AND_VIOLATION_TYPE_NOT_CONSIST;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
