package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

public class DictionaryWithIdAlreadyExistException extends ValidationException {
    public DictionaryWithIdAlreadyExistException(Class entityClass, Long id) {
        super(
                ErrorCode.DICT_WITH_ID_ALREADY_EXISTS,
                String.format(
                        "Dictionary %s with id '%s' already exist",
                        entityClass.getSimpleName(),
                        id
                )
        );
    }
}
