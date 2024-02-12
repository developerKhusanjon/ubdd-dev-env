package uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception;

import uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response.MibApiResponseDTOI;

public class MibApiResultCodeException extends MibApiApplicationException {

    public MibApiResultCodeException(MibApiResponseDTOI response) {
        super(
                String.format("MIB_API_RETURN_RESULT_CODE_%s", response.getResultCode()).toUpperCase(),
                String.format("EMI: Mib service response error: %s - %s", response.getResultCode(), response.getResultMsg())
        );
    }
}
