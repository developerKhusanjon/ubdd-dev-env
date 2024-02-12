package uz.ciasev.ubdd_service.dto.internal.response.user;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.time.LocalDateTime;

@Getter
public class RoleListResponseDTO {

    private Long id;
    private MultiLanguage name;
    private LocalDateTime createdTime;

    public RoleListResponseDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.createdTime = role.getCreatedTime();
    }
}
