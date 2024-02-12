package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action;

import lombok.*;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialDecision;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class MaterialActionContext {

    private CourtMaterial material;
    private CourtMaterialFields courtFields;
    private List<CourtMaterialDecision> materialDecisions;
    private List<Decision> decisions;
    private List<Long> violatorsId;

    public MaterialActionContext resetMaterial(CourtMaterial material) {
        return this.toBuilder().material(material).build();
    }

    public MaterialActionContext resetCourtFields(CourtMaterialFields courtFields) {
        return this.toBuilder().courtFields(courtFields).build();
    }

    public MaterialActionContext resetMaterialDecisions(List<CourtMaterialDecision> materialDecisions) {
        return this.toBuilder().materialDecisions(materialDecisions).build();
    }

    public MaterialActionContext resetDecisions(List<Decision> decisions) {
        return this.toBuilder().decisions(decisions).build();
    }

    public MaterialActionContext resetViolatorsId(List<Long> violatorsId) {
        return this.toBuilder().violatorsId(violatorsId).build();
    }


}
