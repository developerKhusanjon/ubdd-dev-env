package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.part;


import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;

import static uz.ciasev.ubdd_service.entity.dict.court.CourtReturnReasonAlias.APPEAL_WITHDRAWN;
import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.DECISION_MADE;


public class CancelReturnActionPart extends CancelResolvedActionPart {

    public CancelReturnActionPart(CourtActionRequirementServices services) {
        super(services);
    }

    @Override
    protected MaterialActionContext applyTyped(MaterialActionContext context) {
        CourtMaterialFields courtFields = context.getCourtFields();

        if (courtFields.getCourtReturnReason().not(APPEAL_WITHDRAWN)) {
            services.getResolutionActionService().restore(context.getDecisions());

            AdmCase admCase = context.getDecisions().get(0).getResolution().getAdmCase();
            services.getStatusService().setStatusAndSave(admCase, DECISION_MADE);
            // TODO: 06.11.2023  
        }

        return context;
    }
}
