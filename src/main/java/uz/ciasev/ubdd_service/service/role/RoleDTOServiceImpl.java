package uz.ciasev.ubdd_service.service.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.RoleRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.RoleResponseDTO;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.role.RoleRepository;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class RoleDTOServiceImpl implements RoleDTOService {

    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final RoleDTOBuildService roleDTOBuildService;

    @Override
    public RoleResponseDTO create(RoleRequestDTO roleRequestDTO) {
        Role savedRole = roleService.create(roleRequestDTO);
        return roleDTOBuildService.buildResponseDTO(savedRole, null);
    }

    @Override
    public void update(Long id, RoleRequestDTO roleRequestDTO) {
        roleService.update(id, roleRequestDTO);
    }

    @Override
    @Transactional
    public List<RoleResponseDTO> findAll(User user) {
        return buildResponseDTOList(user.isSuperuser()
                ? roleRepository.findSuperuserRoleAndPermissionIds()
                : roleRepository.findOrdinaryRoleAndPermissionIds());
    }

    @Override
    @Transactional
    public RoleResponseDTO findById(Long id) {
        Role role = roleService.getById(id);

        List<Long> permissionsIds = role.getRolePermissions().stream()
                .map(rp -> rp.getPermission().getId())
                .collect(Collectors.toList());

        return roleDTOBuildService.buildResponseDTO(role, permissionsIds);
    }

    @Override
    public void delete(Long id, User user) {
        roleService.delete(id, user);
    }

    @Override
    public List<RoleResponseDTO> findRoleAndPermissionIdsByRole(List<Long> roleIds) {
        return buildResponseDTOList(roleRepository.findRoleAndPermissionIdsByRole(roleIds));
    }

    private List<RoleResponseDTO> buildResponseDTOList(List<Tuple> roleAndPermissionIds) {
        Map<Role, List<Long>> roles = roleAndPermissionIds
                .stream()
                .collect(groupingBy(o -> new Role(
                        (Long) o.get("roleId"),
                        (MultiLanguage) o.get("name"),
                        (LocalDateTime) o.get("createdTime")), mapping(o -> (Long) o.get("permissionId"), toList())));
        return roles
                .entrySet()
                .stream()
                .map(r -> roleDTOBuildService.buildResponseDTO(r.getKey(), r.getValue()))
                .sorted(Comparator.comparing(RoleResponseDTO::getCreatedTime).reversed())
                .collect(Collectors.toList());
    }
}
