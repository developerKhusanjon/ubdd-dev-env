package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;

public interface UpdateDictionaryService<T, D> extends DictionaryService<T> {

    TypeReference<? extends D> getUpdateRequestDTOClass();

    T update(Long id, D request);
}
