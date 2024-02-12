package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.resolution;

import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.CourtActionRequirementServices;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.MaterialActionContext;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.court.CourtMaterialFields;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellationAlias;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;


public class ResolutionGrantedWithNewResolutionCourtAction extends ResolutionCourtAction {

    private final CourtResolutionRequestDTO resolutionRequestDTO;

    public ResolutionGrantedWithNewResolutionCourtAction(CourtActionRequirementServices services, CourtResolutionRequestDTO resolutionRequestDTO) {
        super(services);
        this.resolutionRequestDTO = resolutionRequestDTO;
    }

    @Override
    public MaterialActionContext applyTyped(MaterialActionContext context) {

        CourtMaterialFields courtFields = context.getCourtFields();

        // todo этобудет работать только для 315, пока в деле всегда 1 нарушитель
        // todo причина отмены

        Resolution resolution = context.getDecisions().get(0).getResolution();
        services.getResolutionActionService().cancelResolutionByCourt(resolution, ReasonCancellationAlias.COURT_REVIEW, null);
        services.getStatusService().setStatusAndSave(resolution.getAdmCase(), AdmStatusAlias.SENT_TO_COURT);

        Resolution newResolution = services.getResolutionMainService().createCourtResolution(resolution.getAdmCaseId(), resolutionRequestDTO);

        CourtMaterialFields grantedCourtFields = services.getCourtMaterialFieldsService().grantedWithNewResolution(courtFields, newResolution);

        return context.resetCourtFields(grantedCourtFields);
    }

}