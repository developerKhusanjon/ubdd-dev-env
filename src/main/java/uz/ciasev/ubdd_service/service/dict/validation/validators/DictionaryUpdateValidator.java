package uz.ciasev.ubdd_service.service.dict.validation.validators;

import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;

public interface DictionaryUpdateValidator<T extends AbstractDict, U> extends TypedDictionaryValidator<T, U> {
    void validate(DictionaryServiceWithRepository<T> service, T entity, U request);
}
