package uz.ciasev.ubdd_service.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.PermissionNotAllowedException;
import uz.ciasev.ubdd_service.service.user.SystemUserService;
import uz.ciasev.ubdd_service.service.user.UserPermissionService;
import uz.ciasev.ubdd_service.utils.AllowedPermission;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessPermissionService {

    private final UserPermissionService permissionService;
    private final SystemUserService systemUserService;

    @Before("@annotation(allowedPermission)")
    public void checkPermission(JoinPoint joinPoint, AllowedPermission allowedPermission) {
        User user = systemUserService.getCurrentUserOrThrow();

        boolean hasPermissions = permissionService.isAllowedPermissionForUser(user, allowedPermission.value());

        if (!hasPermissions)
            throw new PermissionNotAllowedException(allowedPermission.value());

    }
}
