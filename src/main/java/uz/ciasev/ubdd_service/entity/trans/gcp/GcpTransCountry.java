package uz.ciasev.ubdd_service.entity.trans.gcp;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.Country;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gcp_country")
@NoArgsConstructor
public class GcpTransCountry extends AbstractSimpleGcpTransEntity<Country> {
}
