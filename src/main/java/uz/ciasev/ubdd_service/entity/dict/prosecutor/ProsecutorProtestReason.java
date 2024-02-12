package uz.ciasev.ubdd_service.entity.dict.prosecutor;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.entity.dict.requests.ProsecutorProtestReasonDTOI;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellation;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ProsecutorProtestReasonCacheDeserializer;

import javax.persistence.*;

@Entity
@Table(name = "d_prosecutor_protest_reason")
@NoArgsConstructor
@JsonDeserialize(using = ProsecutorProtestReasonCacheDeserializer.class)
public class ProsecutorProtestReason extends AbstractEmiDict {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reason_cancellation_id")
    private ReasonCancellation reasonCancellation;

    //    JPA AND CRITERIA FIELDS

    @Column(name = "reason_cancellation_id", insertable = false, updatable = false)
    private Long reasonCancellationId;

    public Long getReasonCancellationId() {
        return reasonCancellation.getId();
    }

    public void construct(ProsecutorProtestReasonDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(ProsecutorProtestReasonDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(ProsecutorProtestReasonDTOI request) {
        this.reasonCancellation = request.getReasonCancellation();
    }
}
