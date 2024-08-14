package uz.ciasev.ubdd_service.mvd_core.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExternalApiAddress {

    private Long regionId;

    private Long countryId;

    private Long districtId;

    private String address;
}
