package uz.ciasev.ubdd_service.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.response.user.*;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.user.UserRepository;
import uz.ciasev.ubdd_service.service.person.PersonService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDTOServiceImpl implements UserDTOService {

    private final UserService userService;
    private final UserAdminService userAdminService;
    private final UserDTOBuildService userDTOBuildService;
    private final PersonService personService;
    private final UserRepository userRepository;
    private final UserRoleService userRoreService;

    @Override
    public UserListResponseDTO findDetailById(User admin, Long id) {
        return convertToListDTO(userAdminService.getByIdForAdminReadonly(admin, id));
    }

    @Override
    public InspectorResponseDTO findInspectorById(Long id) {
        User user = userService.findById(id);
        return new InspectorResponseDTO(user, personService.findById(user.getPersonId()));
    }

    @Override
    public List<RoleResponseDTO> findRolesByUserId(User admin, Long id) {
        return userRoreService.findRoles(id).stream()
                .map(RoleResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsersSimpleResponseDTO> findAllByOrgan(User user) {
        return userRepository.findUsersByOrgan(userService.getUserRelateOrgan(user)).stream()
                .map(UsersSimpleResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Page<UserListResponseDTO> findAllByAdmin(User admin,
                                                    Map<String, String> params,
                                                    Pageable pageable) {

        return userAdminService.findAllByAdmin(admin, params, pageable)
                .map(this::convertToListDTO);
    }

    @Override
    @Transactional
    public Page<UserListLiteResponseDTO> findAllLiteByAdmin(User admin,
                                                            Map<String, String> params,
                                                            Pageable pageable) {

        return userAdminService.findAll(params, pageable)
                .map(UserListLiteResponseDTO::new);
    }

    private UserListResponseDTO convertToListDTO(User user) {
        if (user == null) {
            return null;
        }

        Set<RoleResponseDTO> roles = userRoreService.findRoles(user).stream()
                .map(RoleResponseDTO::new)
                .collect(Collectors.toSet());

        return userDTOBuildService.buildResponseDTO(
                user,
                personService.convertToDTO(personService.findById(user.getPersonId())),
                roles
        );
    }
}
