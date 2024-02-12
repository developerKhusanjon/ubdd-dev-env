package uz.ciasev.ubdd_service.entity.dict.court;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.requests.CourtConsideringBasisCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.CourtConsideringBasisUpdateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.court.CourtConsideringBasisCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_court_considering_basis")
@NoArgsConstructor
@JsonDeserialize(using = CourtConsideringBasisCacheDeserializer.class)
public class CourtConsideringBasis extends CourtAbstractDictEntity {

    @Getter
    private Boolean hasAdditions;

//  todo добавить вместо этого алиас
    @Deprecated
    public CourtConsideringBasis(Long id) {
        this.id = id;
    }

    public void construct(CourtConsideringBasisCreateDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(CourtConsideringBasisUpdateDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(CourtConsideringBasisUpdateDTOI request) {
        this.hasAdditions = request.getHasAdditions();
    }
}