package uz.ciasev.ubdd_service.mvd_core.api;

import lombok.Data;

@Data
public class ExternalApiAddress {

    private Long regionId;

    private Long countryId;

    private Long districtId;

    private String address;
}
