package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

import java.util.List;

public class CodeOfDictionaryNotUniqueException extends ValidationException {
    public CodeOfDictionaryNotUniqueException(Class entityClass, String code, List list) {
        super(
                ErrorCode.CODE_OF_DICTIONARY_NOT_UNIQUE,
                String.format(
                        "Dictionary %s has %s record with code '%s'",
                        entityClass.getSimpleName(),
                        list.size(),
                        code
                )
        );
    }
}
