package uz.ciasev.ubdd_service.repository.trans.gcp;

import org.springframework.data.repository.NoRepositoryBean;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;
import uz.ciasev.ubdd_service.repository.trans.AbstractTransEntityRepository;

@NoRepositoryBean
public interface AbstractSimpleGcpTransEntityRepository<T extends AbstractTransEntity> extends AbstractTransEntityRepository<T> {
    boolean existsByExternalId(Long externalId);
}
