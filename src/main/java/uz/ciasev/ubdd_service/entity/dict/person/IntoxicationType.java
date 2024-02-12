package uz.ciasev.ubdd_service.entity.dict.person;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;

import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.person.IntoxicationTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_intoxication_type")
@NoArgsConstructor
@JsonDeserialize(using = IntoxicationTypeCacheDeserializer.class)
public class IntoxicationType extends AbstractEmiDict {
}
