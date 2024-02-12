package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.requests.VehicleNumberTypeDTOI;
import uz.ciasev.ubdd_service.entity.dict.ubdd.VehicleNumberType;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class VehicleNumberTypeRequestDTO extends BackendDictUpdateRequestDTO<VehicleNumberType> implements VehicleNumberTypeDTOI {

    @NotNull(message = ErrorCode.IS_NEED_SEND_TO_MAIL_REQUIRED)
    private Boolean isNeedSendToMail;

    @NotNull(message = ErrorCode.IS_NEED_SEND_TO_MID_REQUIRED)
    private Boolean isNeedSendToMID;

    @NotNull(message = ErrorCode.IS_NEED_SEND_TO_VAI_REQUIRED)
    private Boolean isNeedMoveToVAI;

    @Override
    public void applyToOld(VehicleNumberType entity) {
        entity.update(this);
    }
}
