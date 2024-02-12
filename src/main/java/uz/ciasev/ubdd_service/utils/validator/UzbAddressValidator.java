package uz.ciasev.ubdd_service.utils.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class UzbAddressValidator implements ConstraintValidator<UzbAddress, AddressRequestDTO> {

    @Autowired
    private Environment environment;

    @Override
    public void initialize(UzbAddress constraintAnnotation) {
    }

    @Override
    public boolean isValid(AddressRequestDTO addressRequestDTO, ConstraintValidatorContext context) {

        Optional<String> publicapi = Arrays.stream(environment.getActiveProfiles()).filter("publicapi"::equals).findAny();
        if (publicapi.isPresent()) {
            return true;
        }

        if (addressRequestDTO == null) {
            return true;
        }
        return Objects.nonNull(addressRequestDTO.getRegion()) && Objects.nonNull(addressRequestDTO.getDistrict());
    }
}
