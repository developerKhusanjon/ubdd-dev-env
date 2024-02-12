package uz.ciasev.ubdd_service.entity.dict.court;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.requests.CourtConsideringAdditionCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.CourtConsideringAdditionUpdateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.court.CourtConsideringAdditionCacheDeserializer;

import javax.persistence.*;

@Entity
@Table(name = "d_court_considering_addition")
@NoArgsConstructor
@JsonDeserialize(using = CourtConsideringAdditionCacheDeserializer.class)
public class CourtConsideringAddition extends CourtAbstractDictEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_considering_basis_id")
    private CourtConsideringBasis courtConsideringBasis;

    //    JPA AND CRITERIA FIELDS

    @Column(name = "court_considering_basis_id", insertable = false, updatable = false)
    private Long courtConsideringBasisId;

    public Long getCourtConsideringBasisId() {
        return courtConsideringBasis.getId();
    }

    public void construct(CourtConsideringAdditionCreateDTOI request) {
        super.construct(request);
        this.courtConsideringBasis = request.getCourtConsideringBasis();
    }

    public void update(CourtConsideringAdditionUpdateDTOI request) {
        super.update(request);
    }
}