package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.Place;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.user.User;

public class NoAccessOnObjectException extends ForbiddenException {

    private NoAccessOnObjectException(String message) {
        super(ErrorCode.NO_ACCESS_ON_OBJECT, message);
    }

    private NoAccessOnObjectException(User user, Place obj, String objName) {
        this(String.format(
                "User(%s) with organ=%s department=%s region=%s district=%s has no access on %s with organ=%s department=%s region=%s district=%s",
                user.getId(), user.getOrganId(), user.getDepartmentId(), user.getRegionId(), user.getDistrictId(),
                objName,
                obj.getOrganId(), obj.getDepartmentId(), obj.getRegionId(), obj.getDistrictId()
        ));
    }

    public NoAccessOnObjectException(User user, AdmCase admCase) {
        this(user, admCase, String.format("adm case (%s)", admCase.getId()));

    }

    public NoAccessOnObjectException(User admin, User user) {
        this(admin, user, String.format("user (%s)", user.getId()));
    }
}
