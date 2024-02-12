package uz.ciasev.ubdd_service.mvd_core.api.tax.dto;

import lombok.Data;

@Data
public class TaxAddressDTO {

    private Long countryId;
    private Long regionId;
    private Long districtId;
    private String address;
}
