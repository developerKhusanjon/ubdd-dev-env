package uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception;

import org.springframework.web.client.HttpStatusCodeException;
import uz.ciasev.ubdd_service.exception.ErrorCode;

public class MibApiResponseHttpStatusCodeException extends MibApiApplicationException {

    public MibApiResponseHttpStatusCodeException(HttpStatusCodeException cause) {
        super(ErrorCode.MIB_API_HTTP_STATUS_CODE_ERROR,
                String.format("Mib service response with http status code %s", cause.getStatusCode()));
    }
}
