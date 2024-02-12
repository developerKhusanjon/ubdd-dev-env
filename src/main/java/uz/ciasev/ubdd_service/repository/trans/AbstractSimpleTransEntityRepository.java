package uz.ciasev.ubdd_service.repository.trans;

import org.springframework.data.repository.NoRepositoryBean;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

@NoRepositoryBean
public interface AbstractSimpleTransEntityRepository<T extends AbstractTransEntity> extends AbstractTransEntityRepository<T> {
    boolean existsByInternalId(Long internalId);
}
