package uz.ciasev.ubdd_service.service.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.RoleRequestDTO;
import uz.ciasev.ubdd_service.entity.permission.Permission;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.role.RolePermission;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.repository.role.RolePermissionRepository;
import uz.ciasev.ubdd_service.repository.role.RoleRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    @Transactional
    public Role create(RoleRequestDTO roleRequestDTO) {
        Role role = roleRequestDTO.buildRole();
        Role savedRole = roleRepository.save(role);

        List<RolePermission> rolePermissions = roleRequestDTO.getPermissions()
                .stream()
                .map(id -> new RolePermission(savedRole, new Permission(id)))
                .collect(toList());

        rolePermissionRepository.saveAll(rolePermissions);

        return savedRole;
    }

    @Override
    @Transactional
    public void update(Long roleId, RoleRequestDTO newRole) {
        Role role = this.getById(roleId);

        role.setName(newRole.getName());

        rolePermissionRepository.removeAllByRoleId(roleId);

        List<RolePermission> rolePermissions = newRole.getPermissions()
                .stream()
                .map(id -> new RolePermission(role, new Permission(id)))
                .collect(toList());

        rolePermissionRepository.saveAll(rolePermissions);
        roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(Role.class, id));
    }

    @Override
    @Transactional
    public void delete(Long id, User user) {
        Role role = this.getById(id);
        roleRepository.delete(role);
    }
}
