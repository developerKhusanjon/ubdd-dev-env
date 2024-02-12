package uz.ciasev.ubdd_service.service.dict.validation.validators;

import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;

public interface DictionaryCreateValidator<T extends AbstractDict, D> extends TypedDictionaryValidator<T, D> {
    void validate(DictionaryServiceWithRepository<T> service, D request);
}
