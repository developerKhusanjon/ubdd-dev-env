package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public interface CreateDictionaryService<T, D> extends DictionaryService<T> {

    TypeReference<? extends D> getCreateRequestDTOClass();

    List<T> create(List<D> request);

    T create(D request);
}
