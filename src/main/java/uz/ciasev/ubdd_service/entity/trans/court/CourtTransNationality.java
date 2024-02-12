package uz.ciasev.ubdd_service.entity.trans.court;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.person.Nationality;
import uz.ciasev.ubdd_service.entity.trans.AbstractSimpleTransEntity;

import javax.persistence.*;

@Entity
@Table(name = "court_trans_nationality")
@NoArgsConstructor
public class CourtTransNationality extends AbstractSimpleTransEntity<Nationality> {
}