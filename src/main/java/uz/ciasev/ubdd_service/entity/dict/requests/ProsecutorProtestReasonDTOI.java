package uz.ciasev.ubdd_service.entity.dict.requests;

import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellation;

public interface ProsecutorProtestReasonDTOI extends DictCreateDTOI, DictUpdateDTOI {
    ReasonCancellation getReasonCancellation();
}
