package uz.ciasev.ubdd_service.entity.dict.resolution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.resolution.DecisionTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_decision_type")
@NoArgsConstructor
@JsonDeserialize(using = DecisionTypeCacheDeserializer.class)
public class DecisionType extends AbstractBackendDict<DecisionTypeAlias> {
}