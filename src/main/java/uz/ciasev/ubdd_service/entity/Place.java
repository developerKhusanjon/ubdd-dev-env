package uz.ciasev.ubdd_service.entity;

import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.dict.Region;

import java.util.Optional;

public interface Place extends RegionDistrict {

    Organ getOrgan();

    Department getDepartment();

    default Long getRegionId() {
        return Optional.ofNullable(getRegion()).map(Region::getId).orElse(null);
    }

    default Long getDistrictId() {
        return Optional.ofNullable(getDistrict()).map(District::getId).orElse(null);
    }

    default Long getOrganId() {
        return Optional.ofNullable(getOrgan()).map(Organ::getId).orElse(null);
    }

    default Long getDepartmentId() {
        return Optional.ofNullable(getDepartment()).map(Department::getId).orElse(null);
    }
}
