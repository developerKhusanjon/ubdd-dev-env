package uz.ciasev.ubdd_service.repository.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface HistoryViewRepository {

    <T> Page<T> findAllPageable(Class<T> entityClass, Pageable pageable);

    <T> Page<T> findAllPageableBySpecification(Class<T> entityClass, Specification<T> specification, Pageable pageable);
}
