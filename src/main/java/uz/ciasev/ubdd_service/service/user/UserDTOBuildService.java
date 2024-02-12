package uz.ciasev.ubdd_service.service.user;

import uz.ciasev.ubdd_service.dto.internal.response.PersonResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.RoleResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.UserListResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.Set;

public interface UserDTOBuildService {

    UserListResponseDTO buildResponseDTO(User user,
                                    PersonResponseDTO person,
                                    Set<RoleResponseDTO> roles);
}
