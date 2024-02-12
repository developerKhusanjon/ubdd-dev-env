package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.request.settings.OrganAccountSettingsCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidOrganAccountSettingsValidator implements ConstraintValidator<ValidOrganAccountSettings, OrganAccountSettingsCreateRequestDTO> {

    @Override
    public void initialize(ValidOrganAccountSettings constraintAnnotation) {
    }

    @Override
    public boolean isValid(OrganAccountSettingsCreateRequestDTO value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        boolean isValid = true;

        if (value.getRelatedRegion() == null) {
            return true;
        }

        if (value.getRelatedRegion().getIsAll() == null) {
            return true;
        }

        boolean isAllRegion = value.getRelatedRegion().getIsAll();
        boolean isDistrictSet = value.getRelatedDistrict() != null;

        if (isAllRegion && isDistrictSet) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.DISTRICT_SELECTION_NOT_ACCEPTED_FOR_IS_ALL_OF_REGION).addConstraintViolation();
            isValid = false;
        }

        if (!isAllRegion && !isDistrictSet) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.DISTRICT_REQUIRED).addConstraintViolation();
            isValid = false;
        }

        if (!isAllRegion && isDistrictSet && value.getRelatedDistrict().isValuePresent()) {
            for (District district : value.getRelatedDistrict().getValue()) {
                if (!district.isPartOfRegion(value.getRelatedRegion().getValue())) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(ErrorCode.REGION_AND_DISTRICT_NOT_CONSIST).addConstraintViolation();
                    isValid = false;
                }
            }
        }


        return isValid;
    }
}
