package uz.ciasev.ubdd_service.service.dict;

public interface DictCreateRequest<T> {

    void applyToNew(T entity);
}
