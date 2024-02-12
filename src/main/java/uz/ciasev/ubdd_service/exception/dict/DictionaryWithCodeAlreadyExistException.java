package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

public class DictionaryWithCodeAlreadyExistException extends ValidationException {
    public DictionaryWithCodeAlreadyExistException(Class entityClass, String code) {
        super(
                ErrorCode.DICT_WITH_CODE_ALREADY_EXISTS,
                String.format(
                        "Dictionary %s with code '%s' already exist",
                        entityClass.getSimpleName(),
                        code
                )
        );
    }
}
