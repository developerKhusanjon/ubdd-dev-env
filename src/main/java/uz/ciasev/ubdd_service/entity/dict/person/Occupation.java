package uz.ciasev.ubdd_service.entity.dict.person;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.court.CourtAbstractDictEntity;
import uz.ciasev.ubdd_service.entity.dict.requests.ExternalDictCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.OccupationCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.OccupationUpdateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.person.OccupationCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_occupation")
@NoArgsConstructor
@JsonDeserialize(using = OccupationCacheDeserializer.class)
public class Occupation extends CourtAbstractDictEntity{

    @Getter
    private Boolean isWorker;

    @Override
    public void construct(ExternalDictCreateDTOI request) {
        super.constructBase(request);
        this.isWorker = false;
    }

    public void construct(OccupationCreateDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(OccupationUpdateDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(OccupationUpdateDTOI request) {
        this.isWorker = request.getIsWorker();
    }

    public Boolean isNotWorker() {
        return !getIsWorker();
    }
}
