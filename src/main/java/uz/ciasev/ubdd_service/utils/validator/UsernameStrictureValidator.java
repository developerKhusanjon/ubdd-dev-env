package uz.ciasev.ubdd_service.utils.validator;

import uz.ciasev.ubdd_service.dto.internal.request.user.UserCreateRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UsernameStrictureValidator implements ConstraintValidator<UsernameStructure, UserCreateRequestDTO> {

    @Override
    public boolean isValid(UserCreateRequestDTO value, ConstraintValidatorContext context) {

        if (value.getUsername() == null) {
            return true;
        }

        String validStructureByDistrict = getValidStructure(
                Optional.ofNullable(value.getDistrict()).map(District::getCode).orElse("00000"),
                Optional.ofNullable(value.getOrgan()).map(Organ::getCode).orElse("000")
                );

        String validStructureByRegion = getValidStructure(
                Optional.ofNullable(value.getRegion()).map(Region::getCode).orElse("00000"),
                Optional.ofNullable(value.getOrgan()).map(Organ::getCode).orElse("000")
                );

        return value.getUsername().startsWith(validStructureByDistrict) || value.getUsername().startsWith(validStructureByRegion);
    }

    private String getValidStructure(String geographyCode, String organCode) {
        return new StringBuilder("ADM_")
                .append(geographyCode)
                .append("_")
                .append(organCode)
                .toString();
    }
}