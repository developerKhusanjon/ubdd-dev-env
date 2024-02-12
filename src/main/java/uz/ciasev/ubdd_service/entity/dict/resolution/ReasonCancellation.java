package uz.ciasev.ubdd_service.entity.dict.resolution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractAliasedDict;

import uz.ciasev.ubdd_service.utils.deserializer.dict.resolution.ReasonCancellationCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_reason_cancellation")
@NoArgsConstructor
@JsonDeserialize(using = ReasonCancellationCacheDeserializer.class)
public class ReasonCancellation extends AbstractAliasedDict<ReasonCancellationAlias> {
    @Override
    protected ReasonCancellationAlias getDefaultAliasValue() {
        return ReasonCancellationAlias.OTHER;
    }
}