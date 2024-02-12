package uz.ciasev.ubdd_service.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.user.UserRole;
import uz.ciasev.ubdd_service.repository.user.UserRoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository repository;

    @Override
    public List<Long> findRolesId(User user) {
        return repository.findAllByUserId(user.getId()).stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> findRoles(User user) {

        return findRoles(user.getId());
    }

    @Override
    public List<Role> findRoles(Long id) {

        return repository.findRolesByUserId(id);
    }

    @Override
    public void removeAllFromUser(Long id) {
        repository.removeAllByUserId(id);
    }

    @Override
    public void removeByUser(Long id, List<Long> roleIds) {
        repository.removeByUserId(id, roleIds);
    }

    @Override
    public void addRolesToUser(User admin, User user, List<Role> roles) {
        List<UserRole> userRoles = roles.stream()
                .map(role -> new UserRole(admin, user, role))
                .collect(Collectors.toList());
        repository.saveAll(userRoles);
    }
}
