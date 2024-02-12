package uz.ciasev.ubdd_service.entity.trans.gcp;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gcp_gender")
@NoArgsConstructor
public class GcpTransGender extends AbstractSimpleGcpTransEntity<Gender> {
}
