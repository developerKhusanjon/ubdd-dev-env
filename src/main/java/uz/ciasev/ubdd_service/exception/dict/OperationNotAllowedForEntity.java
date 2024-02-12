package uz.ciasev.ubdd_service.exception.dict;

import org.springframework.http.HttpStatus;
import uz.ciasev.ubdd_service.exception.ApplicationException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class OperationNotAllowedForEntity extends ApplicationException {
    public OperationNotAllowedForEntity() {
        super(
                HttpStatus.METHOD_NOT_ALLOWED,
                ErrorCode.OPERATION_NOT_ALLOWED_FOR_DICT_ENTITY,
                "Requested operation is not allowed for this dict entity"
        );
    }
}
