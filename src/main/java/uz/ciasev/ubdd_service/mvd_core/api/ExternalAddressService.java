package uz.ciasev.ubdd_service.mvd_core.api;

import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.Address;

public interface ExternalAddressService {

    AddressResponseDTO buildAddressDTO(ExternalApiAddress address);

    Address buildAddress(ExternalApiAddress externalAddress);
}
