package uz.ciasev.ubdd_service.mvd_core.api.court.dto.nine;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.Size;

@Data
public class AddressRequestExternalDTO {

    private Long countryId;

    private Long regionId;

    private Long districtId;

    @Size(max = 200, message = ErrorCode.MAX_ADDRESS_LENGTH)
    private String address;
}
