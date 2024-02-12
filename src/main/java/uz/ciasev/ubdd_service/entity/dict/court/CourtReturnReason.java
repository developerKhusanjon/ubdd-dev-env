package uz.ciasev.ubdd_service.entity.dict.court;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "court_return_reason")
@NoArgsConstructor
public class CourtReturnReason extends CourtAliasedAbstractDictEntity<CourtReturnReasonAlias> {
}
