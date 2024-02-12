package uz.ciasev.ubdd_service.entity.dict;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ParticipantTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_participant_type")
@NoArgsConstructor
@JsonDeserialize(using = ParticipantTypeCacheDeserializer.class)
public class ParticipantType extends AbstractAliasedDict<ParticipantTypeAlias> {
    @Override
    protected ParticipantTypeAlias getDefaultAliasValue() {
        return ParticipantTypeAlias.OTHER;
    }
}
