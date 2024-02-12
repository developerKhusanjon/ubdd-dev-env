package uz.ciasev.ubdd_service.dto.internal.request;

import uz.ciasev.ubdd_service.entity.RegionDistrict;
import uz.ciasev.ubdd_service.utils.validator.ConsistRegionDistrict;

@ConsistRegionDistrict
public interface RegionDistrictRequest extends RegionDistrict {
}
