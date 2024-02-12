package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.resolution;

import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.court.CourtMaterialGroupAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.time.LocalDateTime;

public class ResolutionGrantedCourtAction extends ResolutionCourtAction {

    private final LocalDateTime courtDecisionTime;

    public ResolutionGrantedCourtAction(CourtActionRequirementServices services, LocalDateTime courtDecisionTime) {
        super(services);
        this.courtDecisionTime = courtDecisionTime;
    }

    @Override
    public MaterialActionContext applyTyped(MaterialActionContext context) {
        CourtMaterialFields courtFields = context.getCourtFields();

        CourtMaterialFields grantedCourtFields = services.getCourtMaterialFieldsService().granted(courtFields);

        if (courtFields.getMaterialType().is(CourtMaterialGroupAlias.RETURN_LICENSE)) {
            for (Decision decision : context.getDecisions()) {
                services.getCourtExecutionService().executionLicenseRevocation(decision, courtDecisionTime.toLocalDate());
            }
        }

        return context.resetCourtFields(grantedCourtFields);
    }

}