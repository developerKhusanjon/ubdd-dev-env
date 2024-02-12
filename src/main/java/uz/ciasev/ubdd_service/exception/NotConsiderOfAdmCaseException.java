package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;

public class NotConsiderOfAdmCaseException extends ForbiddenException {

    public NotConsiderOfAdmCaseException(User user, AdmCase admCase) {
        super(
                ErrorCode.NOT_CONSIDER_OF_ADM_CASE,
                String.format("User %s not consider of case %s. This case is being considered by user %s", user.getId(), admCase.getId(), admCase.getConsiderUserId()));
    }
}
