package uz.ciasev.ubdd_service.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.role.Role;
import uz.ciasev.ubdd_service.entity.user.UserRole;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findAllByUserId(Long userId);

    @Query("SELECT ur.role " +
            "FROM UserRole ur " +
            "WHERE ur.user.id = :userId ")
    List<Role> findRolesByUserId(Long userId);

    @Modifying
    @Query("DELETE" +
            " FROM UserRole ur " +
            "WHERE ur.user.id = :userId")
    void removeAllByUserId(@Param(value = "userId") Long userId);

    @Modifying
    @Query("DELETE" +
            " FROM UserRole ur " +
            "WHERE ur.user.id = :userId AND ur.role.id IN :roleIds")
    void removeByUserId(@Param(value = "userId") Long userId, @Param(value = "roleIds") List<Long> roleIds);
}
