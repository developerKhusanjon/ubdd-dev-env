package uz.ciasev.ubdd_service.entity.dict.violation_event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.violation_event.ViolationEventAnnulmentReasonCacheDeserializer;

import javax.persistence.*;

@Entity
@Table(name = "d_violation_event_annulment_reason")
@JsonDeserialize(using = ViolationEventAnnulmentReasonCacheDeserializer.class)
public class ViolationEventAnnulmentReason extends AbstractEmiDict {
}