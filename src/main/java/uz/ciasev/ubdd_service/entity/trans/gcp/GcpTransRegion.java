package uz.ciasev.ubdd_service.entity.trans.gcp;

import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.Region;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gcp_region")
@NoArgsConstructor
public class GcpTransRegion extends AbstractSimpleGcpTransEntity<Region> {
}
