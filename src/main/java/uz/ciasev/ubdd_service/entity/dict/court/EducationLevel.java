package uz.ciasev.ubdd_service.entity.dict.court;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.utils.deserializer.dict.court.EducationLevelCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_education_level")
@NoArgsConstructor
@JsonDeserialize(using = EducationLevelCacheDeserializer.class)
public class EducationLevel extends CourtAbstractDictEntity {
}