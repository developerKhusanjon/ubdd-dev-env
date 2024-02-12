package uz.ciasev.ubdd_service.service.role;

import uz.ciasev.ubdd_service.dto.internal.response.user.RoleResponseDTO;
import uz.ciasev.ubdd_service.entity.role.Role;

import java.util.List;

public interface RoleDTOBuildService {

    RoleResponseDTO buildResponseDTO(Role role, List<Long> permissionIds);
}
