package uz.ciasev.ubdd_service.entity.trans.gcp;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.District;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gcp_district")
@NoArgsConstructor
public class GcpTransDistrict extends AbstractSimpleGcpTransEntity<District> {
}
