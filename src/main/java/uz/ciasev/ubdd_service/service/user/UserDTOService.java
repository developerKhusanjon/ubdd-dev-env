package uz.ciasev.ubdd_service.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.response.user.*;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.Map;

public interface UserDTOService {

    UserListResponseDTO findDetailById(User admin, Long id);

    InspectorResponseDTO findInspectorById(Long id);

    Page<UserListResponseDTO> findAllByAdmin(User admin,
                                             Map<String, String> params,
                                             Pageable pageable);

    List<RoleResponseDTO> findRolesByUserId(User admin, Long id);

    Page<UserListLiteResponseDTO> findAllLiteByAdmin(User user, Map<String, String> params, Pageable pageable);

    List<UsersSimpleResponseDTO> findAllByOrgan(User user);

}
