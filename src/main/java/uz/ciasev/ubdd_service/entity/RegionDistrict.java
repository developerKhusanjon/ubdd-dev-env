package uz.ciasev.ubdd_service.entity;

import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;

public interface RegionDistrict {

    Region getRegion();

    District getDistrict();
}
