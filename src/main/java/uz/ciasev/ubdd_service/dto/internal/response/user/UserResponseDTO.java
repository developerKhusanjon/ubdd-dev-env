package uz.ciasev.ubdd_service.dto.internal.response.user;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.response.PersonResponseDTO;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.Optional;
import java.util.Set;

@Getter
public class UserResponseDTO extends UserListResponseDTO {

    private final Set<String> permissions;
    private String faceIdToken;

    public UserResponseDTO(User user,
                           PersonResponseDTO person,
                           Set<RoleResponseDTO> roles,
                           Set<String> permissions,
                           String faceIdToken) {
        super(user, person, roles);
        this.permissions = Optional.ofNullable(permissions).orElseGet(Set::of);
        this.faceIdToken = faceIdToken;
    }
}
