package uz.ciasev.ubdd_service.repository.protocol;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<T> {

    void refresh(T entity);

    Page<Long> findAllId(Specification<T> specification, Pageable pageable);

    Page<Long> getPagination(Specification<T> specification, Pageable pageable);

    List<Long> findAllIdList(Specification<T> specification, Pageable pageable);

    Optional<Long> findFirstId(Specification<T> specification, Sort sort);

    List<Long> findAllId(Specification<T> specification);

    boolean exists(Specification<T> specification);
}
