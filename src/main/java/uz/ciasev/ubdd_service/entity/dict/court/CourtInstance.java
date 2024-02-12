package uz.ciasev.ubdd_service.entity.dict.court;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "court_instance")
@NoArgsConstructor
public class CourtInstance extends CourtAbstractDictEntity {
}
