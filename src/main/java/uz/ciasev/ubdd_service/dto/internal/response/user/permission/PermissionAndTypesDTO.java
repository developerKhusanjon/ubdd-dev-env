package uz.ciasev.ubdd_service.dto.internal.response.user.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.permission.Permission;
import uz.ciasev.ubdd_service.entity.permission.PermissionType;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionAndTypesDTO {

    private List<PermissionType> types = new ArrayList<>();
    private List<Permission> permissions = new ArrayList<>();
}
