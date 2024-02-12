package uz.ciasev.ubdd_service.entity.dict.court;

import lombok.*;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "court_chancellery_decline_reason")
@NoArgsConstructor
public class CourtDeclineReason extends CourtAbstractDictEntity {

    public CourtDeclineReason(Long id, Long code, MultiLanguage name) {
        this.id = id;
        this.code = code.toString();
        this.name = name;
    }
}
