package uz.ciasev.ubdd_service.entity.dict.person;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.person.CitizenshipCalculatingModeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_citizenship_calculating_mode")
@NoArgsConstructor
@JsonDeserialize(using = CitizenshipCalculatingModeCacheDeserializer.class)
public class CitizenshipCalculatingMode extends AbstractBackendDict<CitizenshipCalculatingModeAlias> {
}
