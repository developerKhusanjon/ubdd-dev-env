package uz.ciasev.ubdd_service.entity.dict.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.PositionCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_position")
@NoArgsConstructor
@JsonDeserialize(using = PositionCacheDeserializer.class)
public class Position extends AbstractEmiDict {


    public Position(Long id) {
        super(id);
    }

}
