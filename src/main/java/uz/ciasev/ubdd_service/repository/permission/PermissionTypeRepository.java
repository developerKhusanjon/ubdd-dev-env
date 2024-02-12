package uz.ciasev.ubdd_service.repository.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.permission.PermissionType;

public interface PermissionTypeRepository extends JpaRepository<PermissionType, Long> {
}
