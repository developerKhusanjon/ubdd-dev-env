package uz.ciasev.ubdd_service.service.address;

import uz.ciasev.ubdd_service.dto.internal.request.GeographyRequest;

import java.util.List;

public interface AddressValidationService {
    void validate(GeographyRequest addressRequestDTO);

    void validateForeignAddress(GeographyRequest addressRequestDTO);

    void validateLocalAddress(GeographyRequest addressRequestDTO);

    List<String> check(GeographyRequest addressRequestDTO);

    List<String> checkLocalAddress(GeographyRequest addressRequestDTO);

    List<String> checkForeignAddress(GeographyRequest addressRequestDTO);
}
