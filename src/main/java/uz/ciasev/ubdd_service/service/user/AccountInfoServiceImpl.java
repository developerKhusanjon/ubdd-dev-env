package uz.ciasev.ubdd_service.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.user.RoleResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.UserResponseDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.service.person.PersonService;
import uz.ciasev.ubdd_service.service.role.RoleDTOService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountInfoServiceImpl implements AccountInfoService {

    private final RoleDTOService roleDTOService;
    private final UserPermissionService permissionService;
    private final PersonService personService;
    private final UserRoleService userRoleService;

    @Override
    public UserResponseDTO getMe(User user) {
        List<Long> roleIds = userRoleService.findRolesId(user);
        List<RoleResponseDTO> roles = roleDTOService.findRoleAndPermissionIdsByRole(roleIds);
        Set<String> permissionAliases = permissionService.findAllAliasByUserId(user).stream().map(String::valueOf).collect(Collectors.toSet());
        Person person = personService.findById(user.getPersonId());

        return new UserResponseDTO(user, personService.convertToDTO(person), Set.copyOf(roles), permissionAliases, user.getFaceIdToken());
    }
}
