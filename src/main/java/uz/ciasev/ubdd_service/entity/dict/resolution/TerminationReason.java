package uz.ciasev.ubdd_service.entity.dict.resolution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.entity.dict.requests.TerminationReasonDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.resolution.TerminationReasonCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_termination_reason")
@NoArgsConstructor
@JsonDeserialize(using = TerminationReasonCacheDeserializer.class)
public class TerminationReason extends AbstractEmiDict {

    @Getter
    private Boolean isParticipateOfRepeatability;

    public void construct(TerminationReasonDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(TerminationReasonDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(TerminationReasonDTOI request) {
        this.isParticipateOfRepeatability = request.getIsParticipateOfRepeatability();
    }
}