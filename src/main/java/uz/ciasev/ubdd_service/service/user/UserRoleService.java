package uz.ciasev.ubdd_service.service.user;

import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;

public interface UserRoleService {

    List<Long> findRolesId(User user);

    List<Role> findRoles(User user);

    List<Role> findRoles(Long id);

    void removeAllFromUser(Long id);

    void removeByUser(Long id, List<Long> roleIds);

    void addRolesToUser(User Admin, User user, List<Role> roles);
}
