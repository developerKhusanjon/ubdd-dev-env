package uz.ciasev.ubdd_service.service.trans;

public interface TransEntityCRDService<T, C> extends
        TransEntityService<T>,
        DeleteTransEntityService<T>,
        CreateTransEntityService<T, C> {
}
