package uz.ciasev.ubdd_service.entity.dict.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.utils.deserializer.dict.RankCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_rank")
@NoArgsConstructor
@JsonDeserialize(using = RankCacheDeserializer.class)
public class Rank extends AbstractEmiDict {

    public Rank(Long id) {
        super(id);
    }

}
