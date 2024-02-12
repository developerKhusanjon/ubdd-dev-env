package uz.ciasev.ubdd_service.entity.trans.mib;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.trans.AbstractSimpleTransEntity;

import javax.persistence.*;

@Entity
@Table(name = "mib_trans_citizenship_type")
@NoArgsConstructor
public class MibTransCitizenshipType extends AbstractSimpleTransEntity<CitizenshipType> {
}
