package uz.ciasev.ubdd_service.dto.internal.request;

import uz.ciasev.ubdd_service.entity.dict.Country;

public interface GeographyRequest extends RegionDistrictRequest {
    Country getCountry();
}
