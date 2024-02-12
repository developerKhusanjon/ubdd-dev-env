package uz.ciasev.ubdd_service.entity.dict.evidence;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.court.CourtAbstractDictEntity;
import uz.ciasev.ubdd_service.entity.dict.requests.EvidenceCategoryCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.requests.EvidenceCategoryUpdateDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.evidence.EvidenceCategoryCacheDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_evidence_category")
@NoArgsConstructor
@JsonDeserialize(using = EvidenceCategoryCacheDeserializer.class)
public class EvidenceCategory extends CourtAbstractDictEntity {

    @Getter
    private Boolean isMoney;

    public void construct(EvidenceCategoryCreateDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(EvidenceCategoryUpdateDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(EvidenceCategoryUpdateDTOI request) {
        this.isMoney = request.getIsMoney();
    }
}