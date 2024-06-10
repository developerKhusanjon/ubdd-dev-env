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

}
