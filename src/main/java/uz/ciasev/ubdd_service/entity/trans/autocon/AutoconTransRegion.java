package uz.ciasev.ubdd_service.entity.trans.autocon;

import lombok.*;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.trans.AbstractSimpleTransEntity;

import javax.persistence.*;

@Entity
@Table(name = "autocon_trans_region")
@NoArgsConstructor
public class AutoconTransRegion extends AbstractSimpleTransEntity<Region> {
}
