package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.part;

import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.entity.court.CourtInvoiceSending;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellationAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.exception.implementation.NotImplementedException;

import java.util.List;
import java.util.stream.Collectors;


public class CanselResolutionActionPart extends CancelResolvedActionPart {

    private final ReasonCancellationAlias cancellationAliasReason;

    public CanselResolutionActionPart(CourtActionRequirementServices services, ReasonCancellationAlias cancellationAliasReason) {
        super(services);
        this.cancellationAliasReason = cancellationAliasReason;
    }

    @Override
    protected MaterialActionContext applyTyped(MaterialActionContext context) {
        CourtMaterialFields courtFields = context.getCourtFields();

        // todo  в ревью надо учитывать что флаг грантед идет по нарушителю
        if (!courtFields.getIsGranted()) return context;

        switch (courtFields.getMaterialType().getGroupAlias()) {
            case RETURN_LICENSE: {
//                todo отменить возврат лицензии
                break;
            }
            case APPEAL_ON_DECISION: {
                Resolution resolution = services.getResolutionService().getById(courtFields.getResolutionId());
                // todo cancelation reason

                services.getResolutionActionService().cancelResolutionByCourt(resolution, cancellationAliasReason, null);
                List<Decision> restoredDecisions = services.getResolutionActionService().restore(context.getDecisions());
                context.resetDecisions(restoredDecisions);
                sendInvoice(resolution, restoredDecisions);
                break;
            }
            case OTHER: {
                // do nothink
                break;
            }
            default: {
                throw new NotImplementedException(String.format("Cansel court resolution for material type %s", courtFields.getMaterialType().getGroupAlias()));
            }
        }

        return context;
    }

    private void sendInvoice(Resolution canceledResolution, List<Decision> restoredDecisions) {
        List<CourtInvoiceSending> sendings = restoredDecisions.stream()
                .map(d -> services.getCourtInvoiceSendingService().buildOnlyMove(canceledResolution.getId(), d))
                .collect(Collectors.toList());

        services.getCourtInvoiceSendingService().create(sendings);

    }

}
