package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;

public class AdmCaseStatusNoSuitableException extends ForbiddenException {

    public AdmCaseStatusNoSuitableException(AdmStatusAlias status, ActionAlias action) {
        super(
                ErrorCode.ADM_CASE_STATUS_NO_SUITABLE,
                String.format("Недопустимое действие '%s' для дела в статусе '%s'", action, status)
        );
    }
}
