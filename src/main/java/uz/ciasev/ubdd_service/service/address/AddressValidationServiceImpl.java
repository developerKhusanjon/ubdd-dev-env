package uz.ciasev.ubdd_service.service.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.request.GeographyRequest;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AddressValidationServiceImpl implements AddressValidationService {

    @Override
    public void validate(GeographyRequest addressRequestDTO) {
        throwOnError(check(addressRequestDTO));
    }

    @Override
    public void validateForeignAddress(GeographyRequest addressRequestDTO) {
        throwOnError(checkForeignAddress(addressRequestDTO));
    }

    @Override
    public void validateLocalAddress(GeographyRequest addressRequestDTO) {
        throwOnError(checkLocalAddress(addressRequestDTO));
    }

    @Override
    public List<String> check(GeographyRequest addressRequestDTO) {

        if (Objects.isNull(addressRequestDTO.getCountry())) {
            return List.of("COUNTRY_REQUIRED");
        }

        return addressRequestDTO.getCountry().isUzbekistan()
                ? checkLocalAddress(addressRequestDTO)
                : checkForeignAddress(addressRequestDTO);
    }

    @Override
    public List<String> checkLocalAddress(GeographyRequest addressRequestDTO) {
        if (addressRequestDTO.getCountry() == null) {
            return List.of("COUNTRY_REQUIRED");
        }

        if (!addressRequestDTO.getCountry().isUzbekistan()) {
            return List.of("LOCAL_ADDRESS_COUNTRY_IS_NOT_UZBEKISTAN");
        }

        Region region = addressRequestDTO.getRegion();
        District district = addressRequestDTO.getDistrict();

        List<String> errors = new ArrayList<>();

        if (Objects.isNull(region)) {
            errors.add("REGION_REQUIRED");
        }

        if (Objects.isNull(district)) {
            errors.add("DISTRICT_REQUIRED");
        }


        if (errors.isEmpty() && !region.getId().equals(district.getRegionId())) {
            errors.add("REGION_AND_DISTRICT_NOT_CONSIST");
        }

        return errors;
    }

    @Override
    public List<String> checkForeignAddress(GeographyRequest addressRequestDTO) {
        if (addressRequestDTO.getCountry() == null) {
            return List.of("COUNTRY_REQUIRED");
        }

        if (addressRequestDTO.getCountry().isUzbekistan()) {
            return List.of("FOREIGN_ADDRESS_COUNTRY_IS_UZBEKISTAN");
        }

        List<String> errors = new ArrayList<>();

        if (Objects.nonNull(addressRequestDTO.getRegion())) {
            errors.add("REGION_MUST_BE_EMPTY");
        }

        if (Objects.nonNull(addressRequestDTO.getDistrict())) {
            errors.add("DISTRICT_MUST_BE_EMPTY");
        }

        return errors;
    }

    private void throwOnError(List<String> errors) {
        if (errors.isEmpty()) return;

        throw new ValidationException(errors.get(0));
    }
}
