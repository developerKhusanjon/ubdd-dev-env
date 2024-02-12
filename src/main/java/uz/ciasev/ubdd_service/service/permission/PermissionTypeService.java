package uz.ciasev.ubdd_service.service.permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.response.user.permission.PermissionTypeResponseDTO;
import uz.ciasev.ubdd_service.entity.permission.PermissionType;

public interface PermissionTypeService {

    Page<PermissionTypeResponseDTO> findAll(Pageable pageable);
    PermissionTypeResponseDTO create(PermissionType permissionType);
}
