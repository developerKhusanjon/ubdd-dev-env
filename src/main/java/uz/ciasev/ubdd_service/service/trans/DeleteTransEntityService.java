package uz.ciasev.ubdd_service.service.trans;

public interface DeleteTransEntityService<T> extends TransEntityService<T> {

    void delete(Long id);
}
