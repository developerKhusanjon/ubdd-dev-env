package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.resolution;

import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialRejectBase;

public class ResolutionRejectCourtAction extends ResolutionCourtAction {

    private final CourtMaterialRejectBase rejectBase;

    public ResolutionRejectCourtAction(CourtActionRequirementServices services, CourtMaterialRejectBase courtRejectBaseId) {
        super(services);
        rejectBase = courtRejectBaseId;
    }

    @Override
    public MaterialActionContext applyTyped(MaterialActionContext context) {
        CourtMaterialFields rejectCourtFields = services.getCourtMaterialFieldsService().reject(context.getCourtFields(), rejectBase);
        return context.resetCourtFields(rejectCourtFields);
    }
}
