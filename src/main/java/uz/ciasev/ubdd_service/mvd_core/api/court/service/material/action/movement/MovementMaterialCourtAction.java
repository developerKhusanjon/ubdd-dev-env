package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.movement;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.entity.court.CourtMaterial;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class MovementMaterialCourtAction implements CourtAction {

    private final CourtActionRequirementServices services;

    private final Long newClimeId;
    private final Region newCourtRegion;
    private final District newCourtDistrict;

    @Override
    public MaterialActionContext apply(MaterialActionContext context) {

        CourtMaterial material = context.getMaterial();
        CourtMaterialFields movedCourtFields = services.getCourtMaterialFieldsService().move(context.getCourtFields(), newClimeId, newCourtRegion, newCourtDistrict);
        material.setClaimId(newClimeId);
        CourtMaterial savedMaterial = services.getCourtMaterialRepository().saveAndFlush(material);

        return context
                .resetMaterial(savedMaterial)
                .resetCourtFields(movedCourtFields);
    }

    @Override
    public CourtActionName getName() {
        return CourtActionName.MOVEMENT;
    }

    @Override
    public Collection<CourtActionName> getConflicts() {
        return List.of(CourtActionName.MERGE, CourtActionName.RESOLUTION, CourtActionName.RETURNING);
    }
}
