package uz.ciasev.ubdd_service.entity.dict.resolution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.AbstractAliasedDict;

import uz.ciasev.ubdd_service.utils.deserializer.dict.resolution.OrganCancellationCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_organ_cancellation")
@NoArgsConstructor
@JsonDeserialize(using = OrganCancellationCacheDeserializer.class)
public class OrganCancellation extends AbstractAliasedDict<OrganCancellationAlias> {
    @Override
    protected OrganCancellationAlias getDefaultAliasValue() {
        return OrganCancellationAlias.OTHER;
    }
}