package uz.ciasev.ubdd_service.service.address;

import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.Address;

public interface AddressService {

    Address findById(Long id);

    Address save(Address address);

    Address copyOf(Address address);

    Address copyOfId(Long addressId);

    Address update(Long id, AddressRequestDTO address);

    AddressResponseDTO findDTOById(Long id);

    AddressResponseDTO convertToDTO(Address address);
}
