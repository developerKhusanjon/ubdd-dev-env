package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.RegionDistrictRequest;
import uz.ciasev.ubdd_service.service.validation.ValidationService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class ConsistRegionDistrictValidator implements ConstraintValidator<ConsistRegionDistrict, RegionDistrictRequest> {

    private final ValidationService validationService;

    @Override
    public void initialize(ConsistRegionDistrict constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegionDistrictRequest entity, ConstraintValidatorContext context) {
        if (entity == null) {
            return true;
        }
        return !validationService.checkDistrictNotInRegion(entity.getRegion(), entity.getDistrict());
    }
}
