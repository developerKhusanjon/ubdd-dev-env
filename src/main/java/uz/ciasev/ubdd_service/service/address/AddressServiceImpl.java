package uz.ciasev.ubdd_service.service.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.AddressRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.AddressRepository;
import uz.ciasev.ubdd_service.service.dict.CountryDictionaryService;
import uz.ciasev.ubdd_service.service.dict.DistrictDictionaryService;
import uz.ciasev.ubdd_service.service.dict.RegionDictionaryService;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CountryDictionaryService countryService;
    private final RegionDictionaryService regionService;
    private final DistrictDictionaryService districtService;

    @Override
    public AddressResponseDTO findDTOById(Long id) {
        return addressRepository.findById(id).map(AddressResponseDTO::new).orElseThrow(() -> new EntityByIdNotFound(Address.class, id));
    }

    @Override
    public AddressResponseDTO convertToDTO(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressResponseDTO(address);
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new EntityByIdNotFound(Address.class, id));
    }

    @Override
    @Transactional
    public Address save(Address address) {
        return addressRepository.saveAndFlush(address);
    }

    @Override
    public Address update(Long id, AddressRequestDTO addressRequestDTO) {
        Address newAddress = addressRequestDTO.apply(findById(id));
        return save(newAddress);
    }

    @Override
    public Address copyOf(Address address) {
        Address rsl = new Address(
                address.getCountryIdOpt().map(countryService::getById).orElse(null),
                address.getRegionIdOpt().map(regionService::getById).orElse(null),
                address.getDistrictIdOpt().map(districtService::getById).orElse(null),
                address.getAddress()
        );
        return save(rsl);
    }

    @Override
    public Address copyOfId(Long addressId) {
        Address address = findById(addressId);
        return copyOf(address);
    }
}
