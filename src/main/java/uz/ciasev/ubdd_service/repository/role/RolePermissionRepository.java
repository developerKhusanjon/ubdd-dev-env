package uz.ciasev.ubdd_service.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.role.RolePermission;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    @Modifying
    @Query("DELETE FROM RolePermission rp " +
            " WHERE rp.role.id = :role_id")
    void removeAllByRoleId(@Param(value = "role_id") Long roleId);

    @Query("SELECT count(*) != 0 " +
            " FROM RolePermission rp INNER JOIN Permission p ON rp.permissionId = p.id AND p.isSuperuserPermission = TRUE " +
            " WHERE rp.role = :role")
    boolean isSuperuserRole(Role role);
}
