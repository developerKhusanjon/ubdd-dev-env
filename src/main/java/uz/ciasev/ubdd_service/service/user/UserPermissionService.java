package uz.ciasev.ubdd_service.service.user;

import uz.ciasev.ubdd_service.entity.permission.PermissionAlias;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface UserPermissionService {

    List<PermissionAlias> findAllAliasByUserId(User user);

    List<String> findAllServicesByUserId(Long userId);

    boolean isAllowedPermissionForUser(User user, PermissionAlias permissionAlias);
}
