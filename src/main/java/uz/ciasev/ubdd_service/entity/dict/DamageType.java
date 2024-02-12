package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.utils.deserializer.dict.DamageTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_damage_type")
@NoArgsConstructor
@JsonDeserialize(using = DamageTypeCacheDeserializer.class)
public class DamageType extends AbstractBackendDict<DamageTypeAlias> {
}
