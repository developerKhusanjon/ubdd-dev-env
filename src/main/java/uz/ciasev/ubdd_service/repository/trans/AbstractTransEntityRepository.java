package uz.ciasev.ubdd_service.repository.trans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import uz.ciasev.ubdd_service.entity.trans.AbstractTransEntity;

@NoRepositoryBean
public interface AbstractTransEntityRepository<T extends AbstractTransEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
}
