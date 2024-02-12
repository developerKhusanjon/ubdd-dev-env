package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ExternalSystemCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "external_system")
@JsonDeserialize(using = ExternalSystemCacheDeserializer.class)
public class ExternalSystem extends AbstractBackendDict<ExternalSystemAlias> {
}