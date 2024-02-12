package uz.ciasev.ubdd_service.repository.dict;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AbstractDictRepository<T extends AbstractDict> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    Optional<T> findByCode(String code);
    List<T> findByCodeAndIsActiveTrue(String code);
}
