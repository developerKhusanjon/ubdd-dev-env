package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

public class AliasDoesNotExistException extends ValidationException {
    public AliasDoesNotExistException(Class<? extends Enum<?>> aliasClass, String name) {
        super(
                ErrorCode.ALIAS_DOES_NOT_EXIST,
                String.format("Alias by name = %s does not exist in %s", name, aliasClass.getSimpleName())
        );
    };
}
