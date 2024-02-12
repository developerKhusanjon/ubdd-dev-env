package uz.ciasev.ubdd_service.service.role;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.user.RoleResponseDTO;
import uz.ciasev.ubdd_service.entity.role.Role;

import java.util.List;

@Service
@Profile("publicapi")
public class PublicApiRoleDTOBuildServiceImpl implements RoleDTOBuildService {

    @Override
    public RoleResponseDTO buildResponseDTO(Role role, List<Long> permissionIds) {
        return new RoleResponseDTO(role, null);
    }
}
