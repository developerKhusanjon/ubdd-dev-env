package uz.ciasev.ubdd_service.entity.dict.court;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.utils.deserializer.dict.court.MaritalStatusCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_marital_status")
@NoArgsConstructor
@JsonDeserialize(using = MaritalStatusCacheDeserializer.class)
public class MaritalStatus extends CourtAbstractDictEntity {
}