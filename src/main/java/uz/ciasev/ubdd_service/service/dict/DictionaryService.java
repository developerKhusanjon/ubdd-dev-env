package uz.ciasev.ubdd_service.service.dict;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DictionaryService<T> {

    String getSubPath();

    Page<T> findAll(Map<String, String> filters, Pageable pageable);

    Optional<T> findById(Long id);

    Object buildListResponseDTO(T entity);

    T getById(Long id);

    default Object buildResponseDTO(T entity) {return buildListResponseDTO(entity);}

    default List<T> findAll() {
        return findAll(Map.of(), Pageable.unpaged()).toList();
    }
}
