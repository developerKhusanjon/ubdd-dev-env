package uz.ciasev.ubdd_service.service.dict;

import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.exception.dict.CodeOfDictionaryNotUniqueException;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.dict.AbstractDictRepository;

import java.util.List;
import java.util.Optional;

public interface DictionaryServiceWithRepository<T extends AbstractDict> extends DictionaryService<T> {

    Class<T> getEntityClass();
    AbstractDictRepository<T> getRepository();

    @Override
    default T getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(getEntityClass(), id));
    }

    default T getActiveByCode(String code) {
        return findActiveByCode(code)
                .orElseThrow(() -> new EntityByParamsNotFound(getEntityClass(), "code", code));
    }

    @Override
    default Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }

    default Optional<T> findActiveByCode(String code) {
        List<T> list = getRepository().findByCodeAndIsActiveTrue(code);
        if (list.isEmpty()) return Optional.empty();
        if (list.size() > 1) throw new CodeOfDictionaryNotUniqueException(getEntityClass(), code, list);
        return Optional.of(list.get(0));
    }

    @Override
    default Object buildListResponseDTO(T entity) {return new DictResponseDTO(entity);}
}