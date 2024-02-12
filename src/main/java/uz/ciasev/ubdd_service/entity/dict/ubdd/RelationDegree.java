package uz.ciasev.ubdd_service.entity.dict.ubdd;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalDictEntity;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd.RelationDegreeDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_relation_degree")
@NoArgsConstructor
@JsonDeserialize(using = RelationDegreeDeserializer.class)
public class RelationDegree extends AbstractExternalDictEntity {
}
