package uz.ciasev.ubdd_service.mvd_core.api.manzil;

import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.Address;

import java.util.Optional;

public interface ManzilService {

    Optional<Address> findAddressByPinpp(String pinpp);

    Optional<AddressResponseDTO> findAddressDTOByPinpp(String pinpp);
}
