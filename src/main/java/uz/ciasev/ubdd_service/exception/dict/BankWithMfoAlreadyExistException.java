package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

public class BankWithMfoAlreadyExistException extends ValidationException {
    public BankWithMfoAlreadyExistException(Class entityClass, String code) {
        super(
                ErrorCode.BANK_WITH_MFO_ALREADY_EXISTS,
                String.format(
                        "Dictionary %s with mfo '%s' already exist",
                        entityClass.getSimpleName(),
                        code
                )
        );
    }
}
