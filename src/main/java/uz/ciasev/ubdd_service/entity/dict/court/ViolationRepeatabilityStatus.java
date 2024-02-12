package uz.ciasev.ubdd_service.entity.dict.court;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.requests.ViolationRepeatabilityStatusCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.ViolationRepeatabilityStatusUpdateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.court.ViolationRepeatabilityStatusCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_violation_repeatability_status")
@NoArgsConstructor
@JsonDeserialize(using = ViolationRepeatabilityStatusCacheDeserializer.class)
public class ViolationRepeatabilityStatus extends CourtAbstractDictEntity {

    @Getter
    private boolean isNeedEarlierViolatedArticleParts;

    public void construct(ViolationRepeatabilityStatusCreateDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(ViolationRepeatabilityStatusUpdateDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(ViolationRepeatabilityStatusUpdateDTOI request) {
        this.isNeedEarlierViolatedArticleParts = request.getIsNeedEarlierViolatedArticleParts();
    }
}