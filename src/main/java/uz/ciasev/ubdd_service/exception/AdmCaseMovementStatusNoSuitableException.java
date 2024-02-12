package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseMovementStatusAlias;

public class AdmCaseMovementStatusNoSuitableException extends ForbiddenException {
    public AdmCaseMovementStatusNoSuitableException(AdmCaseMovementStatusAlias permittedStatus) {
        super(
                ErrorCode.ADM_CASE_MOVEMENT_STATUS_NOT_SUITABLE,
                String.format("This action is only allowed for adm case movement with movement status %s", permittedStatus.name())
        );
    }
}
