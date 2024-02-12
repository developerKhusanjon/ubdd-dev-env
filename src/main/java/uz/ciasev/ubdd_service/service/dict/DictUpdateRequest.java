package uz.ciasev.ubdd_service.service.dict;

public interface DictUpdateRequest<T> {

    void applyToOld(T entity);
}
