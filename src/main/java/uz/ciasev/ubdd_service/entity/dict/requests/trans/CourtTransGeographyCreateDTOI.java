package uz.ciasev.ubdd_service.entity.dict.requests.trans;

import uz.ciasev.ubdd_service.dto.internal.request.GeographyRequest;

public interface CourtTransGeographyCreateDTOI extends GeographyRequest {
    Long getExternalCountryId();
    Long getExternalRegionId();
    Long getExternalDistrictId();
}
