package uz.ciasev.ubdd_service.dto.internal.response.adm.mib;

import uz.ciasev.ubdd_service.mvd_core.api.mib.api.exception.MibApiApplicationException;
import uz.ciasev.ubdd_service.mvd_core.api.mib.api.types.MibResult;
import uz.ciasev.ubdd_service.dto.internal.response.SendResponse;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;

import java.util.function.Supplier;

public class MibSendResponseBuilder {

    public static SendResponse of(MibApiApplicationException e) {
        return SendResponse.failure(e.getSendResponseCode(), e.getSendResponseMessage());
    }

    public static SendResponse of(MibResult sendResult, Supplier<Object> data) {
        MibSendStatus status = sendResult.getStatus();

        if (status.isSuccessfully()) {
            return SendResponse.success(status.getCode(), sendResult.getMessage(), data.get());
        } else {
            return SendResponse.failure(status.getCode(), sendResult.getMessage());
        }
    }
}
