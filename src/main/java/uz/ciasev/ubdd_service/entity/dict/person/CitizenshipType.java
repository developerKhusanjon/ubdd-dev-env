package uz.ciasev.ubdd_service.entity.dict.person;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.person.CitizenshipTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_citizenship_type")
@NoArgsConstructor
@JsonDeserialize(using = CitizenshipTypeCacheDeserializer.class)
public class CitizenshipType extends AbstractBackendDict<CitizenshipTypeAlias> {
}
