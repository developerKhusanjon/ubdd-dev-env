package uz.ciasev.ubdd_service.entity.court;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialRejectBase;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "court_material_fields")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class CourtMaterialFields extends CourtFields {

    @Getter
    private Long materialId;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "court_material_type_id")
    private CourtMaterialType materialType;

    @Getter
    @Setter
    private Boolean isGranted;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "reject_base_id")
    private CourtMaterialRejectBase rejectBase;

    public Long getMaterialTypeId() {
        if (materialType == null) return null;
        return this.materialType.getId();
    }

    public Long getRejectBaseId() {
        if (rejectBase == null) return null;
        return this.rejectBase.getId();
    }

    public void setMaterial(@NotNull CourtMaterial courtMaterial) {
        materialId = courtMaterial.getId();
    }

    public void setMaterialOf(@NotNull CourtMaterialFields otherField) {
        materialId = otherField.materialId;
    }
}