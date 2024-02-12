package uz.ciasev.ubdd_service.entity.dict.court;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "court_changing_reason")
@NoArgsConstructor
public class CourtChangingReason extends CourtAbstractDictEntity {
}
