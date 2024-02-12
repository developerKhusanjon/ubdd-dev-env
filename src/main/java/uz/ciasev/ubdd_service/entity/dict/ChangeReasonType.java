package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ChangeReasonTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_adm_case_change_reason")
@NoArgsConstructor
@JsonDeserialize(using = ChangeReasonTypeCacheDeserializer.class)
public class ChangeReasonType extends AbstractEmiDict {
}
