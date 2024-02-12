package uz.ciasev.ubdd_service.exception;

import uz.ciasev.ubdd_service.entity.permission.PermissionAlias;

public class PermissionNotAllowedException extends ForbiddenException {

    public PermissionNotAllowedException(PermissionAlias permissionAlias) {
        super(
                ErrorCode.PERMISSION_NOT_ALLOWED_EXCEPTION,
                String.format("You need '%s' user permission for access to this endpoint", permissionAlias.name())
        );
    }
}
