package uz.ciasev.ubdd_service.repository.user;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.user.UserListExcelProjection;
import uz.ciasev.ubdd_service.entity.user.UsersSimpleProjection;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends UserCustomRepository, JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsernameIgnoreCase(String username);

//    @Query(value = "WITH cte AS (" +
//            " SELECT u.* FROM {h-schema}users u " +
//            " INNER JOIN " +
//            " (SELECT us.region_id, us.district_id, us.organ_id, us.department_id " +
//            "    FROM {h-schema}users us " +
//            "    WHERE us.id = :adminId) adm " +
//            " ON (u.region_id = adm.region_id OR adm.region_id IS NULL) " +
//            "   AND (u.district_id = adm.district_id OR adm.district_id IS NULL) " +
//            "   AND (u.organ_id = adm.organ_id OR adm.organ_id IS NULL) " +
//            "   AND (u.department_id = adm.department_id OR adm.department_id IS NULL) " +
//            " ) " +
//            " SELECT u.* " +
//            "   FROM cte u " +
//            " WHERE u.id = :id "
//            , nativeQuery = true)
//    Optional<User> findByIdByAdmin(@Param("adminId") Long adminId, @Param("id") Long id);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE UPPER(u.workCertificate) = UPPER(:workCertificate) " +
            "AND u.organId = 12 " +
            "AND u.isActive = TRUE ")
    Optional<User> findByGaiWorkCertificate(String workCertificate);

    @Query("SELECT u " +
            " FROM User u " +
            "WHERE u.username = :username " +
            "  AND u.id != :id")
    Optional<User> findUniqueUsername(@Param("id") Long id,
                                      @Param("username") String username);

    @Query("SELECT COUNT(DISTINCT u) " +
            "FROM User u " +
            "WHERE (:organId = 0L OR u.organId = :organId) " +
            "AND u.person.pinpp = :pinpp " +
            "AND u.isActive = TRUE ")
    Optional<Long> countUsersByPinpp(@Param("organId") Long organId, @Param("pinpp") String pinpp);

    @Query("SELECT " +
            "u.id AS id, " +
            "u.person.pinpp AS pinpp, " +
            "u.person.lastNameLat AS lastName, " +
            "u.person.firstNameLat AS firstName, " +
            "u.person.secondNameLat AS secondName, " +
            "u.region.id AS regionId, " +
            "u.district.id AS districtId, " +
            "u.isActive AS isActive " +
            "FROM User u " +
            "WHERE u.organ = :organ ")
    List<UsersSimpleProjection> findUsersByOrgan(Organ organ);


    @Query("SELECT u " +
            " FROM User u " +
            "WHERE u.needSetPassword = TRUE ")
    List<User> findForSetNewPassword();


    @Query("SELECT u " +
            " FROM User u " +
            "WHERE u.needGeneratePassword = TRUE ")
    List<User> findForPasswordGeneration();

    @Query("SELECT " +
            "u.lastNameLat AS lastName, " +
            "u.firstNameLat AS firstName, " +
            "u.secondNameLat AS secondName, " +
            "u.username AS username, " +
            "u.workCertificate AS workCertificate, " +
            "u.mobile AS mobile, " +
            "u.landline AS landline, " +
            "u.documentSeries AS documentSeries, " +
            "u.documentNumber AS documentNumber, " +
            "jsonb_extract_path_text(o.name, 'lat') AS organName, " +
            "jsonb_extract_path_text(dep.name, 'lat') AS organDepartmentName, " +
            "jsonb_extract_path_text(mainR.name, 'lat') AS regionName, " +
            "jsonb_extract_path_text(branchR.name, 'lat') AS branchRegionName, " +
            "jsonb_extract_path_text(dist.name, 'lat') AS districtName, " +
            "ur.roles AS roles, " +
            "jsonb_extract_path_text(us.name, 'lat') AS userStatusName, " +
            "jsonb_extract_path_text(rank.name, 'lat') AS rankName, " +
            "jsonb_extract_path_text(p.name, 'lat') AS positionName " +
            "FROM User u " +
            "JOIN UserStatus us ON u.statusId = us.id " +
            "JOIN Rank rank ON rank.id = u.rankId " +
            "LEFT JOIN Position p ON p.id = u.positionId " +
            "LEFT JOIN Organ o ON u.organId = o.id " +
            "LEFT JOIN Department dep ON u.departmentId = dep.id " +
            "LEFT JOIN Region mainR ON u.regionId = mainR.id " +
            "LEFT JOIN District dist ON u.districtId = dist.id " +
            "LEFT JOIN Region branchR ON branchR.id = u.branchRegionId " +
            "LEFT JOIN UserRoleView ur ON ur.id = u.id " +
            "WHERE u.id IN :ids")
    List<UserListExcelProjection> findUserExcelProjectionByIds(List<Long> ids, Sort sort);
}
