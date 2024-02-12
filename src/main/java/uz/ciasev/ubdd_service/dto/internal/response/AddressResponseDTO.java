package uz.ciasev.ubdd_service.dto.internal.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import uz.ciasev.ubdd_service.mvd_core.api.ExternalApiAddress;
import uz.ciasev.ubdd_service.entity.Address;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AddressResponseDTO {

    private final Long id;
    private final Long countryId;
    private final Long regionId;
    private final Long districtId;
    private final String address;

    public AddressResponseDTO(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
        this.countryId = address.getCountryId();
        this.regionId = address.getRegionId();
        this.districtId = address.getDistrictId();
    }

    public AddressResponseDTO(ExternalApiAddress address) {
        this.id = null;
        this.address = address.getAddress();
        this.countryId = address.getCountryId();
        this.regionId = address.getRegionId();
        this.districtId = address.getDistrictId();
    }
}
