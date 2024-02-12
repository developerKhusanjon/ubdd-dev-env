package uz.ciasev.ubdd_service.dto.internal.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.role.Role;

import java.util.List;
import java.util.Optional;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoleResponseDTO extends RoleListResponseDTO {

    private final List<Long> permissions;

    public RoleResponseDTO(Role role, List<Long> permissions) {
        super(role);
        this.permissions = Optional.ofNullable(permissions).orElseGet(List::of);
    }

    public RoleResponseDTO(Role role) {
        super(role);
        this.permissions = List.of();
    }
}
