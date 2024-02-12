package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.user.User;

public class NoAccessOnSuperuserRoleException extends ForbiddenException {

    public NoAccessOnSuperuserRoleException(User admin, Role superuserRole) {
        super(
                ErrorCode.ORDINARY_ADMIN_USE_SUPERUSER_ROLE,
                String.format("Ordinary user(%s) has no access to superuser role %s-%s", admin.getId(), superuserRole.getId(), superuserRole.getName().getRu())
                );
    }
}
