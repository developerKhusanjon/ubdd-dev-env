package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.returns;

import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.entity.dict.court.CourtReturnReason;


public class ReturnWithdrawnMaterialCourtAction extends ReturnCourtAction {

    public ReturnWithdrawnMaterialCourtAction(CourtActionRequirementServices services, CourtReturnReason courtReturnReason) {
        super(services, courtReturnReason);
    }

    @Override
    public MaterialActionContext applyTyped(MaterialActionContext context) {
        return context;
    }
}
