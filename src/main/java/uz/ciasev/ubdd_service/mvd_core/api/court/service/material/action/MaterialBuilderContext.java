package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialDecision;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class MaterialBuilderContext {

    private final CourtMaterial material;
    private final CourtMaterialFields courtMaterialFields;
    private final List<CourtMaterialDecision> materialDecisions;
    private final List<Long> violatorsId;

}
