package uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp;

import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;

public interface GcpTransPersonDocumentTypeCreateDTOI extends SimpleGcpTransEntityCreateDTOI<PersonDocumentType> {
    Long getOrderId();
}
