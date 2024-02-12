package uz.ciasev.ubdd_service.utils.validator;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.service.address.AddressValidationService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


@RequiredArgsConstructor
public class AddressValidator implements ConstraintValidator<ValidAddress, AddressRequestDTO> {

    private final AddressValidationService validationService;

    @Override
    public void initialize(ValidAddress constraintAnnotation) {
    }

    @Override
    public boolean isValid(AddressRequestDTO addressRequestDTO, ConstraintValidatorContext context) {
        if (addressRequestDTO == null) {
            return true;
        }

        List<String> errors = validationService.check(addressRequestDTO);
        errors.forEach(error -> context
                .buildConstraintViolationWithTemplate("REGION_REQUIRED")
                .addConstraintViolation());

        return errors.isEmpty();

    }

//    private boolean validateLocalAddress(AddressRequestDTO addressRequestDTO, ConstraintValidatorContext context) {
//        Region region = addressRequestDTO.getRegion();
//        District district = addressRequestDTO.getDistrict();
//
//        boolean isValid = true;
//
//        if (Objects.isNull(region)) {
//            isValid = false;
//            context
//                    .buildConstraintViolationWithTemplate("REGION_REQUIRED")
//                    .addConstraintViolation();
//        }
//
//        if (Objects.isNull(district)) {
//            isValid = false;
//            context.buildConstraintViolationWithTemplate("DISTRICT_REQUIRED")
//                    .addConstraintViolation();
//        }
//
//
//        if (isValid && !region.getId().equals(district.getRegionId())) {
//            isValid = false;
//            context
//                    .buildConstraintViolationWithTemplate("REGION_AND_DISTRICT_NOT_CONSIST")
//                    .addConstraintViolation();
//        }
//
//        return isValid;
//    }
//
//    private boolean validateForeignAddress(AddressRequestDTO addressRequestDTO, ConstraintValidatorContext context) {
//        boolean isValid = true;
//
//        if (Objects.nonNull(addressRequestDTO.getRegion())) {
//            isValid = false;
//            context
//                    .buildConstraintViolationWithTemplate("REGION_MUST_BE_EMPTY")
//                    .addConstraintViolation();
//        }
//
//        if (Objects.nonNull(addressRequestDTO.getDistrict())) {
//            isValid = false;
//            context.buildConstraintViolationWithTemplate("DISTRICT_MUST_BE_EMPTY")
//                    .addConstraintViolation();
//        }
//
//        return isValid;
//    }
}
