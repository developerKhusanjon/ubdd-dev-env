package uz.ciasev.ubdd_service.entity.dict.ubdd;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd.UBDDGroupDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_ubdd_group")
@NoArgsConstructor
@JsonDeserialize(using = UBDDGroupDeserializer.class)
public class UBDDGroup extends UbddAbstractDictEntity {
}
