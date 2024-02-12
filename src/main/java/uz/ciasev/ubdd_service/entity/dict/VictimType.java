package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.utils.deserializer.dict.VictimTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_victim_type")
@NoArgsConstructor
@JsonDeserialize(using = VictimTypeCacheDeserializer.class)
public class VictimType extends AbstractBackendDict<VictimTypeAlias> {
}
