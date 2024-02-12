package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.requests.TerminationReasonDTOI;
import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class TerminationReasonRequestDTO extends BaseDictRequestDTO implements TerminationReasonDTOI, DictCreateRequest<TerminationReason>, DictUpdateRequest<TerminationReason> {

    @NotNull(message = ErrorCode.IS_PARTICIPATED_OF_REPEATABILITY_REQUIRED)
    private Boolean isParticipateOfRepeatability;

    @Override
    public void applyToNew(TerminationReason entity) {
        entity.construct(this);
    }

    @Override
    public void applyToOld(TerminationReason entity) {
        entity.update(this);
    }
}
