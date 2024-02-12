package uz.ciasev.ubdd_service.entity.dict.resolution;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.NoArgsConstructor;

import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;
import uz.ciasev.ubdd_service.entity.dict.requests.PunishmentTypeDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.resolution.PunishmentTypeCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_punishment_type")
@NoArgsConstructor
@JsonDeserialize(using = PunishmentTypeCacheDeserializer.class)
public class PunishmentType extends AbstractBackendDict<PunishmentTypeAlias> {

    @Getter
    private Long courtAdditionalPunishmentId;

    public void update(PunishmentTypeDTOI request) {
        super.update(request);
        this.courtAdditionalPunishmentId = request.getCourtAdditionalPunishmentId();
    }
}