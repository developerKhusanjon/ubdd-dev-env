package uz.ciasev.ubdd_service.service.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.response.user.permission.PermissionResponseDTO;
import uz.ciasev.ubdd_service.entity.permission.Permission;
import uz.ciasev.ubdd_service.repository.permission.PermissionRepository;


@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public PermissionResponseDTO create(Permission permission) {
        return new PermissionResponseDTO(permissionRepository.save(permission));
    }

    @Override
    @Transactional
    public Page<PermissionResponseDTO> findAll(Pageable pageable) {
        return permissionRepository
                .findAll(pageable)
                .map(PermissionResponseDTO::new);
    }
}
