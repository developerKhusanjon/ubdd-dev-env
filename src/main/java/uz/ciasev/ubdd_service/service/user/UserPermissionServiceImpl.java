package uz.ciasev.ubdd_service.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.permission.PermissionAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.permission.PermissionRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPermissionServiceImpl implements UserPermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public List<PermissionAlias> findAllAliasByUserId(User user) {
        if (isAllPermissionUser(user)) {
            return Arrays.asList(PermissionAlias.values());
        }

        return permissionRepository.findAllAliasByUserId(user.getId());
    }

    @Override
    public List<String> findAllServicesByUserId(Long userId) {
        return permissionRepository.findAllServicesByUserId(userId);
    }

    @Override
    public boolean isAllowedPermissionForUser(User user, PermissionAlias permissionAlias) {
        if (isAllPermissionUser(user)) {
            return true;
        }

        return permissionRepository.existAliasByUserId(user.getId(), permissionAlias);
    }

    private boolean isAllPermissionUser(User user) {
        return user.isGod();
    }
}
