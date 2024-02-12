package uz.ciasev.ubdd_service.service.permission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.ciasev.ubdd_service.dto.internal.response.user.permission.PermissionResponseDTO;
import uz.ciasev.ubdd_service.entity.permission.Permission;

public interface PermissionService {

    PermissionResponseDTO create(Permission permission);

    Page<PermissionResponseDTO> findAll(Pageable pageable);
}
