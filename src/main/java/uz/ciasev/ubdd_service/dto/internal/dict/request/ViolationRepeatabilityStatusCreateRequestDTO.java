package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationRepeatabilityStatus;
import uz.ciasev.ubdd_service.entity.dict.requests.ViolationRepeatabilityStatusCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ViolationRepeatabilityStatusCreateRequestDTO extends ViolationRepeatabilityStatusUpdateRequestDTO implements ViolationRepeatabilityStatusCreateDTOI, DictCreateRequest<ViolationRepeatabilityStatus> {

    @NotNull(message = ErrorCode.ID_REQUIRED)
    private Long id;

    @Override
    public void applyToNew(ViolationRepeatabilityStatus entity) {
        entity.construct(this);
    }
}
