package uz.ciasev.ubdd_service.service.dict.validation;

import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;
import uz.ciasev.ubdd_service.service.dict.DictionaryServiceWithRepository;

public interface DictionaryValidationService {

    <T extends AbstractDict, D extends DictCreateRequest<T>> void validateCreate(DictionaryServiceWithRepository<T> service, D request);

    <T extends AbstractDict, U extends DictUpdateRequest<T>> void validateUpdate(DictionaryServiceWithRepository<T> service, T entity, U request);
}
