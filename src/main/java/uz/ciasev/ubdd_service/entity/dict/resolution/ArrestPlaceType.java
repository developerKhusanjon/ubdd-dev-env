package uz.ciasev.ubdd_service.entity.dict.resolution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;

import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.resolution.ArrestPlaceTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_arrest_place_type")
@NoArgsConstructor
@JsonDeserialize(using = ArrestPlaceTypeCacheDeserializer.class)
public class ArrestPlaceType extends AbstractEmiDict {
}
