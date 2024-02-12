package uz.ciasev.ubdd_service.entity.dict.admcase;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.DeclineReasonDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_decline_reason")
@NoArgsConstructor
@JsonDeserialize(using = DeclineReasonDeserializer.class)
public class DeclineReason extends AbstractEmiDict {
}
