package uz.ciasev.ubdd_service.service.trans;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface TransEntityService<T> {

    String getSubPath();

    Page<T> findAll(Map<String, String> filters, Pageable pageable);

    Optional<T> findById(Long id);

    Object buildListResponseDTO(T entity);

    T getById(Long id);

    default Object buildResponseDTO(T entity) {return buildListResponseDTO(entity);}

}
