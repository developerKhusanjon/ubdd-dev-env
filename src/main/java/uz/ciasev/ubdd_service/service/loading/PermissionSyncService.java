package uz.ciasev.ubdd_service.service.loading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.permission.Permission;
import uz.ciasev.ubdd_service.entity.permission.PermissionAlias;
import uz.ciasev.ubdd_service.entity.permission.PermissionType;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.role.RolePermission;
import uz.ciasev.ubdd_service.repository.permission.PermissionRepository;
import uz.ciasev.ubdd_service.repository.permission.PermissionTypeRepository;
import uz.ciasev.ubdd_service.repository.role.RolePermissionRepository;
import uz.ciasev.ubdd_service.repository.role.RoleRepository;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionSyncService {

    private final PermissionRepository permissionRepository;
    private final PermissionTypeRepository permissionTypeRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleRepository roleRepository;

    public void sync() {
        Set<PermissionAlias> declaredAliases = new HashSet<>(List.of(PermissionAlias.values()));
        declaredAliases.remove(PermissionAlias.UNKNOWN);

        //  добавить в базы не доастающие
        Set<PermissionAlias> existValues = permissionRepository.findAll().stream().map(Permission::getAlias).collect(Collectors.toSet());
        PermissionType type = permissionTypeRepository.getOne(1L);
        Role role = roleRepository.getOne(-1L);

        declaredAliases
                .stream()
                .filter(alias -> !existValues.contains(alias))
                .map(alias -> {
                    String text = String.valueOf(alias).toLowerCase(Locale.ROOT).replace("_", " ");

                    return Permission.builder()
                            .alias(alias)
                            .name(new MultiLanguage(text, text, text))
                            .permissionType(type)
                            .isSuperuserPermission(false)
                            .service("core")
                            .description(text)
                            .build();
                })
                .map(permissionRepository::save)
                .map(p -> new RolePermission(role, p))
                .forEach(rolePermissionRepository::save);
    }
}
