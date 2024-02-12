package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.editing.EditingOfResolutionAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.editing.EditingOfReturningAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.movement.MovementMaterialCourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.registration.InformationCourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.registration.RegistrationCourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.resolution.ResolutionGrantedCourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.resolution.ResolutionGrantedWithNewResolutionCourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.resolution.ResolutionRejectCourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.returns.ReturnForProcessCourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.returns.ReturnWithdrawnMaterialCourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.review.ReviewCourtAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.review.ReviewResolutionAction;
import uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action.review.ReviewReturnAction;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.court.CourtResolutionRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.court.*;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourtActionFactory {

    private final CourtActionRequirementServices actionRequirementServices;

    public ReviewCourtAction createReviewReturnAction(Long newClaimId) {
        return new ReviewReturnAction(
                actionRequirementServices,
                newClaimId
        );
    }

    public ReviewCourtAction createReviewResolutionAction(Long newClaimId) {
        return new ReviewResolutionAction(
                actionRequirementServices,
                newClaimId);
    }

    public RegistrationCourtAction createRegistrationAction(
            CourtMaterialType materialType,
            CourtStatus courtStatus,
            CourtTransfer court,
            Long instance,
            String judgeInfo,
            LocalDateTime hearingDate,
            String caseNumber,
            Boolean isProtest,
            Boolean isVccUsed,
            List<RegistrationCourtAction.CourtViolatorData> violatorsData
    ) {
        return new RegistrationCourtAction(
                actionRequirementServices,
                materialType,
                courtStatus,
                court.getRegion(),
                court.getDistrict(),
                instance,
                judgeInfo,
                hearingDate,
                caseNumber,
                isProtest,
                isVccUsed,
                violatorsData
        );
    }

    public MovementMaterialCourtAction createMovementAction(Long newClimeId, CourtTransfer court) {
        return new MovementMaterialCourtAction(
                actionRequirementServices,
                newClimeId,
                court.getRegion(),
                court.getDistrict()
        );
    }

    public ReturnWithdrawnMaterialCourtAction createReturnWithdrawnMaterialAction(CourtReturnReason courtReturnReason) {
        return new ReturnWithdrawnMaterialCourtAction(
                actionRequirementServices,
                courtReturnReason
        );
    }

    public ReturnForProcessCourtAction createReturnForProcessAction(CourtReturnReason courtReturnReason) {
        return new ReturnForProcessCourtAction(
                actionRequirementServices,
                courtReturnReason
        );
    }

    public ResolutionGrantedCourtAction createResolutionGrantedAction(LocalDateTime courtDecisionTime) {
        return new ResolutionGrantedCourtAction(actionRequirementServices, courtDecisionTime);
    }

    public ResolutionGrantedWithNewResolutionCourtAction createResolutionGrantedWithNewResolutionAction(CourtResolutionRequestDTO resolutionRequestDTO) {
        return new ResolutionGrantedWithNewResolutionCourtAction(actionRequirementServices, resolutionRequestDTO);
    }

    public ResolutionRejectCourtAction createResolutionRejectAction(CourtMaterialRejectBase materialRejectBase) {
        return new ResolutionRejectCourtAction(actionRequirementServices, materialRejectBase);
    }

    public EditingOfResolutionAction createEditingOfResolutionAction() {
        return new EditingOfResolutionAction(
                actionRequirementServices
        );
    }

    public EditingOfReturningAction createEditingOfReturningAction() {
        return new EditingOfReturningAction(actionRequirementServices);
    }

    public InformationCourtAction createInformationAction(CourtStatus status) {
        return new InformationCourtAction(actionRequirementServices, status);
    }
}
