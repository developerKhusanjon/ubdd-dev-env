package uz.ciasev.ubdd_service.service.trans;

public interface TransEntityCreateRequest<T> {

    void applyToNew(T entity);
}
