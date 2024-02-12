package uz.ciasev.ubdd_service.mvd_core.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.AddressResponseDTO;
import uz.ciasev.ubdd_service.entity.Address;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.service.dict.CountryDictionaryService;
import uz.ciasev.ubdd_service.service.dict.DistrictDictionaryService;
import uz.ciasev.ubdd_service.service.dict.RegionDictionaryService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExternalAddressServiceImpl implements ExternalAddressService {

    private final RegionDictionaryService regionDictionaryService;
    private final CountryDictionaryService countryDictionaryService;
    private final DistrictDictionaryService districtDictionaryService;

    @Override
    public Address buildAddress(ExternalApiAddress externalAddress) {
        Country country = countryDictionaryService.getById(externalAddress.getCountryId());
        Region region = Optional.ofNullable(externalAddress.getRegionId())
                .map(regionDictionaryService::getById)
                .orElse(null);
        District district = Optional.ofNullable(externalAddress.getDistrictId())
                .map(districtDictionaryService::getById)
                .orElse(null);

        return new Address(country, region, district, externalAddress.getAddress());

        //  На светлую память
        // return addressService.create(address);
    }

    @Override
    public AddressResponseDTO buildAddressDTO(ExternalApiAddress address) {
        if (address == null) {
            return null;
        }
        return new AddressResponseDTO(address);
    }
}
