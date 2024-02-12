package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.returns;

import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.entity.dict.court.CourtReturnReason;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellationAlias;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

import static uz.ciasev.ubdd_service.entity.status.AdmStatusAlias.RETURN_FROM_COURT;

public class ReturnForProcessCourtAction extends ReturnCourtAction {

    public ReturnForProcessCourtAction(CourtActionRequirementServices services, CourtReturnReason courtReturnReason) {
        super(services, courtReturnReason);
    }

    @Override
    public MaterialActionContext applyTyped(MaterialActionContext context) {

        if (context.getMaterialDecisions().size() != 1)
            throw new NotImplementedException("Material resolution implement only for one violator");
        // todo при материале на нкоторых учасников дела надо ДЕЛАТЬ ВЫДЕЛЕНИЕ
        // todo при решениях суда надо брать для отмены только некоторые решения из резолюции

        Resolution resolution = context.getDecisions().get(0).getResolution();
        services.getResolutionActionService().cancelResolutionByCourt(resolution, ReasonCancellationAlias.COURT_REVIEW, null);

        services.getStatusService().setStatusAndSave(resolution.getAdmCase(), RETURN_FROM_COURT);

        return context;
    }
}
