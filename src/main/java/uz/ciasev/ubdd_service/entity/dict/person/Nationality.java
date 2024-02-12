package uz.ciasev.ubdd_service.entity.dict.person;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.person.NationalityCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_nationality")
@NoArgsConstructor
@JsonDeserialize(using = NationalityCacheDeserializer.class)
public class Nationality extends AbstractEmiDict {
}
