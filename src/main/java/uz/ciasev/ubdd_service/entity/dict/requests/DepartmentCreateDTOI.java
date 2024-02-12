package uz.ciasev.ubdd_service.entity.dict.requests;

import uz.ciasev.ubdd_service.entity.dict.Organ;

public interface DepartmentCreateDTOI extends DictCreateDTOI, DepartmentUpdateDTOI {
    Organ getOrgan();
}
