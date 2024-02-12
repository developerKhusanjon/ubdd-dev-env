package uz.ciasev.ubdd_service.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.PersonResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.RoleResponseDTO;
import uz.ciasev.ubdd_service.dto.internal.response.user.UserListResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Profile("!publicapi")
public class UserDTOBuildServiceImpl implements UserDTOBuildService {

    @Override
    public UserListResponseDTO buildResponseDTO(User user, PersonResponseDTO person, Set<RoleResponseDTO> roles) {
        return new UserListResponseDTO(user, person, roles);
    }
}
