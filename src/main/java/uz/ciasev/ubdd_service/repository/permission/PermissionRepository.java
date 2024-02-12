package uz.ciasev.ubdd_service.repository.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.permission.Permission;
import uz.ciasev.ubdd_service.entity.permission.PermissionAlias;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("SELECT DISTINCT p.alias " +
            " FROM UserRole ur " +
            " JOIN RolePermission rp ON ur.roleId = rp.roleId " +
            " JOIN Permission p ON rp.permissionId = p.id " +
            "WHERE ur.userId = :userId")
    List<PermissionAlias> findAllAliasByUserId(Long userId);

    @Query("SELECT COUNT(p.id) > 0 " +
            " FROM UserRole ur " +
            " JOIN RolePermission rp ON ur.roleId = rp.roleId " +
            " JOIN Permission p ON rp.permissionId = p.id " +
            "WHERE ur.userId = :userId AND p.alias = :alias")
    boolean existAliasByUserId(Long userId, PermissionAlias alias);

    @Query("SELECT DISTINCT p.service " +
            " FROM UserRole ur " +
            " JOIN RolePermission rp ON ur.roleId = rp.roleId " +
            " JOIN Permission p ON rp.permissionId = p.id " +
            "WHERE ur.userId = :userId")
    List<String> findAllServicesByUserId(Long userId);
}
