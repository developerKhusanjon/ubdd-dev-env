package uz.ciasev.ubdd_service.entity.dict.requests.trans;

import uz.ciasev.ubdd_service.dto.internal.request.RegionDistrictRequest;

public interface MailTransGeographyCreateDTOI extends RegionDistrictRequest {

    Long getExternalRegionId();

    Long getExternalDistrictId();
}
