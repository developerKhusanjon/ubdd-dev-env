package uz.ciasev.ubdd_service.entity.dict.court;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ViolationPlaceTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_violation_place_type")
@NoArgsConstructor
@JsonDeserialize(using = ViolationPlaceTypeCacheDeserializer.class)
public class ViolationPlaceType extends CourtAbstractDictEntity {
}