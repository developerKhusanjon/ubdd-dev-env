package uz.ciasev.ubdd_service.service.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.response.user.permission.PermissionTypeResponseDTO;
import uz.ciasev.ubdd_service.entity.permission.PermissionType;
import uz.ciasev.ubdd_service.repository.permission.PermissionTypeRepository;

@Service
@RequiredArgsConstructor
public class PermissionTypeServiceImpl implements PermissionTypeService {

    private final PermissionTypeRepository permissionTypeRepository;

    @Override
    public Page<PermissionTypeResponseDTO> findAll(Pageable pageable) {
        return permissionTypeRepository
                .findAll(pageable)
                .map(PermissionTypeResponseDTO::new);
    }

    @Override
    public PermissionTypeResponseDTO create(PermissionType permissionType) {
        return new PermissionTypeResponseDTO(permissionTypeRepository.save(permissionType));
    }
}
