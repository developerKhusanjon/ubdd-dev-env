package uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp;

import uz.ciasev.ubdd_service.dto.internal.request.GeographyRequest;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

public interface GcpTransDivisionCreateDTOI extends GeographyRequest, GcpTransEntityCreateDTOI {
    MultiLanguage getName();
    String getAddress();
}
