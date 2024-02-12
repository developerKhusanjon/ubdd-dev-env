package uz.ciasev.ubdd_service.service.dict.validation.validators;

import uz.ciasev.ubdd_service.entity.dict.AbstractDict;

public interface TypedDictionaryValidator<T extends AbstractDict, R> {
    Class<T> getValidatedType();
    Class<R> getRequestType();
}
