package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationRepeatabilityStatus;
import uz.ciasev.ubdd_service.entity.dict.requests.ViolationRepeatabilityStatusUpdateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ViolationRepeatabilityStatusUpdateRequestDTO extends ExternalDictUpdateRequestDTO<ViolationRepeatabilityStatus> implements ViolationRepeatabilityStatusUpdateDTOI {

    @NotNull(message = ErrorCode.IS_NEED_EARLIER_VIOLATED_ARTICLE_PARTS_REQUIRED)
    private Boolean isNeedEarlierViolatedArticleParts;

    @Override
    public void applyToOld(ViolationRepeatabilityStatus entity) {
        entity.update(this);
    }
}
