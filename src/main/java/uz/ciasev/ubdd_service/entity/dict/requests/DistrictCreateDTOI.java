package uz.ciasev.ubdd_service.entity.dict.requests;

import uz.ciasev.ubdd_service.entity.dict.Region;

public interface DistrictCreateDTOI extends DictCreateDTOI, DistrictUpdateDTOI {
    Region getRegion();
}
