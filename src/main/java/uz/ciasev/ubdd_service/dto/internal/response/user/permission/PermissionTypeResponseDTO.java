package uz.ciasev.ubdd_service.dto.internal.response.user.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.permission.PermissionType;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionTypeResponseDTO {

    private Long id;
    private MultiLanguage name;

    public PermissionTypeResponseDTO(PermissionType permissionType) {
        this.id = permissionType.getId();
        this.name = permissionType.getName();
    }
}
