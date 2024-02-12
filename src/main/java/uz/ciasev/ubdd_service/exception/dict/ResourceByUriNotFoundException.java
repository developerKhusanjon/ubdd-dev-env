package uz.ciasev.ubdd_service.exception.dict;

import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.exception.NotFoundException;

public class ResourceByUriNotFoundException extends NotFoundException {
    public ResourceByUriNotFoundException(String path) {
        super(
                ErrorCode.RESOURCE_BY_URI_NOT_FOUND,
                String.format("No relevant service found for URI /v0/api/%s", path)
        );
    }
}
