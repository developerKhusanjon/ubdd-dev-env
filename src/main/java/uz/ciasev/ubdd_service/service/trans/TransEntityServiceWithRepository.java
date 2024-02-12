package uz.ciasev.ubdd_service.service.trans;

import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.trans.AbstractTransEntityRepository;

import java.util.Optional;

public interface TransEntityServiceWithRepository<T extends AbstractTransEntity> extends TransEntityService<T> {

    Class<T> getEntityClass();
    AbstractTransEntityRepository<T> getRepository();

    @Override
    default T getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(getEntityClass(), id));
    }

    @Override
    default Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }
}