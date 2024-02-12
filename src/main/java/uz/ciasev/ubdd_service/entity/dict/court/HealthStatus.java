package uz.ciasev.ubdd_service.entity.dict.court;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.utils.deserializer.dict.court.HealthStatusCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_health_status")
@NoArgsConstructor
@JsonDeserialize(using = HealthStatusCacheDeserializer.class)
public class HealthStatus extends CourtAbstractDictEntity {
}