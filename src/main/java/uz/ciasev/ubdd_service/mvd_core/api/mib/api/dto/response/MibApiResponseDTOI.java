package uz.ciasev.ubdd_service.mvd_core.api.mib.api.dto.response;

import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;

public interface MibApiResponseDTOI {
    String getResultCode();
    String getResultMsg();
    Long getEnvelopeId();

    default boolean isSuccessfully() {
        return MibSendStatus.isSuccessfullyCode(getResultCode());
    }
}
