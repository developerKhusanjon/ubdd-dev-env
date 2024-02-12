package uz.ciasev.ubdd_service.entity.dict.court;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_court_performance_type")
@NoArgsConstructor
public class CourtPerformanceType extends CourtAliasedAbstractDictEntity<CourtPerformanceTypeAlias> {
}
