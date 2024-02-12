package uz.ciasev.ubdd_service.entity.dict.court;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalStatusDictEntity;
import uz.ciasev.ubdd_service.entity.dict.AliasedDictEntity;
import uz.ciasev.ubdd_service.utils.deserializer.dict.court.CourtStatusCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "court_status")
@NoArgsConstructor
@JsonDeserialize(using = CourtStatusCacheDeserializer.class)
public class CourtStatus extends AbstractExternalStatusDictEntity implements AliasedDictEntity<CourtStatusAlias> {

    @Getter
    @Enumerated(EnumType.STRING)
    protected CourtStatusAlias alias;

    // информативные статаусы суда, например передача в архив.
    @Getter
    private boolean isInformationOnly;
}
