package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConsistOrganDepartmentValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsistOrganDepartment {

    String message() default ErrorCode.ORGAN_AND_DEPARTMENT_NOT_CONSIST;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
