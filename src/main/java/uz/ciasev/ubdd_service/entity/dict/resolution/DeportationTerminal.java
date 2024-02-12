package uz.ciasev.ubdd_service.entity.dict.resolution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.resolution.DeportationTerminalCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_deportation_terminal")
@NoArgsConstructor
@JsonDeserialize(using = DeportationTerminalCacheDeserializer.class)
public class DeportationTerminal extends AbstractEmiDict {
}
