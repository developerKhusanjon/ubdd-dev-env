package uz.ciasev.ubdd_service.entity.dict.requests.trans;

public interface SimpleTransEntityCreateDTOI<T> {

    T getInternal();

    Long getExternalId();
}
