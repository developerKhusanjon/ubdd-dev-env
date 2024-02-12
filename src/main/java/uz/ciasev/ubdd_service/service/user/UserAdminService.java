package uz.ciasev.ubdd_service.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import uz.ciasev.ubdd_service.dto.internal.request.user.UserCreateRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.request.user.UserRequestDTO;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.user.UserListExcelProjection;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface UserAdminService {

    User create(User admin, UserCreateRequestDTO userRequestDTO);

    void update(User admin, Long id, UserRequestDTO userRequestDTO);

    User getByIdForAdminReadonly(User admin, Long id);

    User getByIdForAdmin(User admin, Long id);

    void delete(User admin, Long id);

    void rebind(User admin, Long id, String f1DocId);

    Page<User> findAllByAdmin(User admin, Map<String, String> params, Pageable pageable);

    Stream<UserListExcelProjection> finadAllExcelProjectionByFilter(User user, Map<String, String> filters, int limit, Sort sort);

    Page<User> findAll(Map<String, String> params,
                       Pageable pageable);

    void changePassByUserId(User admin, Long id, String pass);

    void addRolesByUserId(User admin, Long id, List<Role> roles);

    void replaceRolesByUserId(User admin, Long id, List<Role> roles);

    void deleteRolesByUserId(User admin, Long id, List<Role> roles);
}
