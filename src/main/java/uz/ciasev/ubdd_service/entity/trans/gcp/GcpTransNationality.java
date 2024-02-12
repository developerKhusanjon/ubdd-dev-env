package uz.ciasev.ubdd_service.entity.trans.gcp;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.person.Nationality;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gcp_nationality")
@NoArgsConstructor
public class GcpTransNationality extends AbstractSimpleGcpTransEntity<Nationality> {
}
