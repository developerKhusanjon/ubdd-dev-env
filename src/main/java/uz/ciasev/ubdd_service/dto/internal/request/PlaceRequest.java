package uz.ciasev.ubdd_service.dto.internal.request;

import uz.ciasev.ubdd_service.utils.validator.ConsistOrganDepartment;
import uz.ciasev.ubdd_service.utils.validator.ConsistRegionDistrict;

@ConsistRegionDistrict
@ConsistOrganDepartment
public interface PlaceRequest extends RegionDistrictRequest, OrganDepartmentRequest {
}
