package uz.ciasev.ubdd_service.service.role;

import uz.ciasev.ubdd_service.dto.internal.request.RoleRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.RoleResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface RoleDTOService {

    RoleResponseDTO create(RoleRequestDTO roleRequestDTO);

    void update(Long id, RoleRequestDTO roleRequestDTO);

    List<RoleResponseDTO> findAll(User user);

    RoleResponseDTO findById(Long id);

    void delete(Long id, User user);

    List<RoleResponseDTO> findRoleAndPermissionIdsByRole(List<Long> roleIds);
}
