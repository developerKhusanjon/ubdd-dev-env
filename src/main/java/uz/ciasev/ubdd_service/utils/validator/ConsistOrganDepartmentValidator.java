package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.OrganDepartmentRequest;
import uz.ciasev.ubdd_service.service.validation.ValidationService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class ConsistOrganDepartmentValidator implements ConstraintValidator<ConsistOrganDepartment, OrganDepartmentRequest> {

    private final ValidationService validationService;

    @Override
    public boolean isValid(OrganDepartmentRequest entity, ConstraintValidatorContext context) {
        if (entity == null) {
            return true;
        }
        return !validationService.checkDepartmentNotInOrgan(entity.getOrgan(), entity.getDepartment());
    }
}
