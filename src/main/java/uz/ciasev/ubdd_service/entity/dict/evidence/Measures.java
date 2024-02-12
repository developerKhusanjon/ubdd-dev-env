package uz.ciasev.ubdd_service.entity.dict.evidence;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.court.CourtAbstractDictEntity;
import uz.ciasev.ubdd_service.utils.deserializer.dict.evidence.MeasuresCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_measure")
@NoArgsConstructor
@JsonDeserialize(using = MeasuresCacheDeserializer.class)
public class Measures extends CourtAbstractDictEntity {
}