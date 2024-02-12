package uz.ciasev.ubdd_service.service.role;

import uz.ciasev.ubdd_service.dto.internal.request.RoleRequestDTO;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface RoleService {

    Role create(RoleRequestDTO roleRequestDTO);

    void update(Long id, RoleRequestDTO roleRequestDTO);

    List<Role> findAll();

    Role getById(Long id);

    void delete(Long id, User user);
}
