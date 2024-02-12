package uz.ciasev.ubdd_service.entity.dict.court;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "d_court_material_reject_base")
@NoArgsConstructor
public class CourtMaterialRejectBase extends CourtAbstractDictEntity {
}
