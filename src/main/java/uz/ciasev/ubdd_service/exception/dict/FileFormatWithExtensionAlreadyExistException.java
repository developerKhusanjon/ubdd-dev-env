package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

public class FileFormatWithExtensionAlreadyExistException extends ValidationException {
    public FileFormatWithExtensionAlreadyExistException(Class entityClass, String code) {
        super(
                ErrorCode.FILE_FORMAT_WITH_EXTENSION_ALREADY_EXISTS,
                String.format(
                        "Dictionary %s with extension '%s' already exist",
                        entityClass.getSimpleName(),
                        code
                )
        );
    }
}
