package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.admcase.AdmCaseDeletionRequest;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestStatusAlias;

public class AdmCaseDeletionRequestStatusNoSuitableException extends ForbiddenException {

    public AdmCaseDeletionRequestStatusNoSuitableException(AdmCaseDeletionRequest request, String appliedActionName, AdmCaseDeletionRequestStatusAlias suitableStatuse) {
        super(
                ErrorCode.ADM_CASE_DELETION_REQUEST_STATUS_NO_SUITABLE,
                String.format(
                        "Adm case deletion request in status %s not allow action %s. This action allowed for status %s",
                        request.getStatus(),
                        appliedActionName,
                        suitableStatuse
                )
        );
    }
}
