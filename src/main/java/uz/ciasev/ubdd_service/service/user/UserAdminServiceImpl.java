package uz.ciasev.ubdd_service.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.mvd_core.api.f1.dto.F1Document;
import uz.ciasev.ubdd_service.dto.internal.request.user.UserCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.user.UserRequestDTO;
import uz.ciasev.ubdd_service.entity.Person;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.user.UserListExcelProjection;
import uz.ciasev.ubdd_service.repository.user.UserRepository;
import uz.ciasev.ubdd_service.service.history.HistoryService;
import uz.ciasev.ubdd_service.service.main.PersonDataService;
import uz.ciasev.ubdd_service.service.search.ChunkedSearchService;
import uz.ciasev.ubdd_service.service.validation.UserValidationService;
import uz.ciasev.ubdd_service.specifications.UserSpecifications;
import uz.ciasev.ubdd_service.utils.EncryptUtils;
import uz.ciasev.ubdd_service.utils.filters.FilterHelper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {

    private final UserRepository userRepository;
    private final PersonDataService admPersonService;
    private final UserRoleService userRoleService;
    private final UserValidationService validationService;
    private final UserSpecifications userSpecifications;
    private final HistoryService historyService;
    private final FilterHelper<User> filterHelper;
    private final ChunkedSearchService<UserListExcelProjection> chunkedSearchService;
    private final UserService userService;


    @Override
    @Transactional
    public User create(User admin, UserCreateRequestDTO userRequestDTO) {

        validationService.checkAdministrationAllow(userRequestDTO.getOrgan());

        validationService.validate(admin, Optional.empty(), userRequestDTO, () -> userRepository.findByUsernameIgnoreCase(userRequestDTO.getUsername()));
        validationService.validateUserUniqueness(userRequestDTO.getOrgan(), userRequestDTO.getPinpp());


        Pair<Person, F1Document> personWithDocument = admPersonService.provideByPinpp(userRequestDTO.getPinpp());
        F1Document f1Document = personWithDocument.getSecond();
        Person person = personWithDocument.getFirst();
        String userPhotoUrl = admPersonService.getPhotoByDocument(f1Document);

        User user = userRequestDTO.buildUser(person, userPhotoUrl, f1Document);
        user.setPassword(EncryptUtils.encodePassword(user.getPassword()));

        User savedUser = userRepository.save(user);

        historyService.registerUserEditing("create", null, savedUser, null);



        return savedUser;
    }

    @Override
    @Transactional
    public void update(User admin, Long id, UserRequestDTO requestDTO) {

        validationService.validate(admin, Optional.of(id), requestDTO, null);
        User user = getByIdForAdmin(admin, id);

        User editedUser = requestDTO.applyTo(user);

        // ВАЖНО, ДОЛЖНО НАХОДИТЬСЯ ДО СЭЙВА. МОДЕЛЬ ОБНАВЛЯЕТЬСЯ
        historyService.registerUserEditing("update", user, editedUser, null);
        userRepository.save(editedUser);

    }

    @Override
    public User getByIdForAdminReadonly(User admin, Long id) {
        User user = userService.findById(id);
        validationService.validateAdminAccessOnUser(admin, user);
        return user;
    }

    @Override
    public User getByIdForAdmin(User admin, Long id) {
        User user = getByIdForAdminReadonly(admin, id);
        validationService.checkAdministrationAllow(user.getOrgan());
        return user;
    }

    @Override
    @Transactional
    public void delete(User admin, Long id) {
        User user = getByIdForAdmin(admin, id);

        User editedUser = user.toBuilder()
                .isActive(false)
                .build();

        // ВАЖНО, ДОЛЖНО НАХОДИТЬСЯ ДО СЭЙВА. МОДЕЛЬ ОБНАВЛЯЕТЬСЯ
        historyService.registerUserEditing("delete", user, editedUser, null);
        userRepository.save(editedUser);

    }

    @Override
    @Transactional
    public void rebind(User admin, Long id, String f1DocId) {

        User user = getByIdForAdmin(admin, id);

        validationService.validateUserUniqueness(user.getOrgan(), f1DocId);


        Pair<Person, F1Document> personWithDocument = admPersonService.provideByPinpp(f1DocId);
        F1Document f1Document = personWithDocument.getSecond();

        Person person = personWithDocument.getFirst();
        String userPhotoUrl = admPersonService.getPhotoByDocument(f1Document);

        User editedUser = user.toBuilder()
                .person(person)
                .lastNameLat(person.getLastNameLat())
                .firstNameLat(person.getFirstNameLat())
                .secondNameLat(person.getSecondNameLat())
                .userPhotoUri(userPhotoUrl)
                .documentSeries(f1Document.getSeries())
                .documentNumber(f1Document.getNumber())
                .build();

        // ВАЖНО, ДОЛЖНО НАХОДИТЬСЯ ДО СЭЙВА. МОДЕЛЬ ОБНАВЛЯЕТЬСЯ
        historyService.registerUserEditing("rebind", user, editedUser, null);
        userRepository.save(editedUser);

    }

    @Override
    public Page<User> findAllByAdmin(User admin,
                                     Map<String, String> params,
                                     Pageable pageable) {

        return userRepository.findAll(
                userSpecifications.inUserVisibility(admin).and(filterHelper.getParamsSpecification(params)),
                pageable);
    }

    @Override
    public Stream<UserListExcelProjection> finadAllExcelProjectionByFilter(User user, Map<String, String> filters, int limit, Sort sort) {
        List<Long> ids = userRepository.findAllId(
                userSpecifications.inUserVisibility(user).and(filterHelper.getParamsSpecification(filters))
        );

        return chunkedSearchService.findAllExcelProjectionByIds(
                ids,
                limit,
                longs -> userRepository.findUserExcelProjectionByIds(longs, sort)
        );
    }

    @Override
    public Page<User> findAll(Map<String, String> params,
                              Pageable pageable) {

        return userRepository.findAll(
                filterHelper.getParamsSpecification(params),
                pageable);
    }

    @Override
    @Transactional
    public void changePassByUserId(User admin, Long id, String pass) {

        User user = getByIdForAdmin(admin, id);

        user.setPassword(EncryptUtils.encodePassword(pass));

        userRepository.save(user);

    }

    @Override
    @Transactional
    public void addRolesByUserId(User admin, Long id, List<Role> roles) {

        validationService.validateRoleList(admin, roles);

        User user = getByIdForAdmin(admin, id);

        List<Role> userRoles = userRoleService.findRoles(id);

        List<Role> filteredRoles = roles.stream().filter(r -> !userRoles.contains(r)).collect(Collectors.toList());

        historyService.registerUserEditing("addRole", user, user, roles);
        userRoleService.addRolesToUser(admin, user, filteredRoles);

    }

    @Override
    @Transactional
    public void replaceRolesByUserId(User admin, Long id, List<Role> roles) {

        validationService.validateRoleList(admin, roles);

        User user = getByIdForAdmin(admin, id);

        userRoleService.removeAllFromUser(id);

        historyService.registerUserEditing("replaceRole", user, user, roles);
        userRoleService.addRolesToUser(admin, user, roles);


    }

    @Override
    @Transactional
    public void deleteRolesByUserId(User admin, Long id, List<Role> roles) {
        validationService.validateRoleList(admin, roles);

        User user = getByIdForAdmin(admin, id);

        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());

        historyService.registerUserEditing("deleteRole", user, user, roles);
        userRoleService.removeByUser(id, roleIds);

    }

}