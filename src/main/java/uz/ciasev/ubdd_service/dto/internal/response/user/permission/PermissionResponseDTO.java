package uz.ciasev.ubdd_service.dto.internal.response.user.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.permission.Permission;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponseDTO {

    private Long id;
    private MultiLanguage name;
    private String alias;
    private String description;
    private PermissionTypeResponseDTO permissionType;
    private Boolean isSuperuserPermission;

    public PermissionResponseDTO(Permission permission) {
        this.id = permission.getId();
        this.name = permission.getName();
        this.alias = permission.getAlias().name();
        this.description = permission.getDescription();
        this.permissionType = new PermissionTypeResponseDTO(permission.getPermissionType());
        this.isSuperuserPermission = permission.getIsSuperuserPermission();
    }
}
