package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.ValidationException;

public class RequestBodyMappingException extends ValidationException {
    public RequestBodyMappingException(String message) {
        super(
                ErrorCode.REQUEST_BODY_CANNOT_BE_MAPPED_TO_JSON,
                message
        );
    };
}
