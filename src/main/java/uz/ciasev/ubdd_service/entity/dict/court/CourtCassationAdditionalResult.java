package uz.ciasev.ubdd_service.entity.dict.court;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "court_cassation_additional_result")
@NoArgsConstructor
public class CourtCassationAdditionalResult extends CourtAbstractDictEntity {
}
