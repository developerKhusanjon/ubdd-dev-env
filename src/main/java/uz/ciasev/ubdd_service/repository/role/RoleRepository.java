package uz.ciasev.ubdd_service.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.role.Role;

import javax.persistence.Tuple;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r.name AS name, r.id AS roleId, rp.permission.id AS permissionId, r.createdTime AS createdTime " +
            " FROM Role r " +
            " JOIN RolePermission rp " +
            "   ON r.id = rp.role.id " +
            "GROUP BY r.id, rp.permission.id " +
            "ORDER BY r.createdTime")
    List<Tuple> findSuperuserRoleAndPermissionIds();

    @Query("SELECT r.name AS name, r.id AS roleId, rp.permission.id AS permissionId, r.createdTime AS createdTime " +
            " FROM Role r " +
            " JOIN RolePermission rp " +
            "   ON r.id = rp.role.id " +
            " WHERE r.id NOT IN (" +
            "   SELECT rp.roleId " +
            "    FROM RolePermission rp JOIN Permission p ON rp.permissionId = p.id " +
            "   WHERE p.isSuperuserPermission = TRUE " +
            ")" +
            "GROUP BY r.id, rp.permission.id " +
            "ORDER BY r.createdTime")
    List<Tuple> findOrdinaryRoleAndPermissionIds();

    @Query("" +
            "SELECT r.name AS name, r.id AS roleId, rp.permissionId AS permissionId, r.createdTime AS createdTime " +
            "  FROM Role r " +
            "  JOIN RolePermission rp " +
            "    ON r.id = rp.roleId " +
            " GROUP BY r.id, rp.permissionId HAVING r.id IN :ids")
    List<Tuple> findRoleAndPermissionIdsByRole(@Param("ids") List<Long> ids);
}
