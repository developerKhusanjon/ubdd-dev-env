package uz.ciasev.ubdd_service.repository.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.settings.OrganRegisteredArticlePartSettings;


public interface OrganRegisteredArticlePartSettingsRepository extends JpaRepository<OrganRegisteredArticlePartSettings, Long>, OrganRegisteredArticlePartSettingsCustomRepository, JpaSpecificationExecutor<OrganRegisteredArticlePartSettings> {


//    @Query("SELECT " +
//            "s.id as id, " +
//            "s.articlePart.id as articlePartId, " +
//            "s.articlePart.isDiscount as isDiscount, " +
//            "s.articlePart.isActive as isActive, " +
//            "s.articlePart.articleId as articleId " +
//            "FROM OrganRegisteredArticlePartSettings s " +
//            "WHERE organId = :organId AND departmentId = :departmentId ")
//    List<OrganArticleSettingsProjection> findAllProjectionByOrganIdAndDepartmentId(Long organId, Long departmentId);
//
//
//    @Query("SELECT " +
//            "s.id as id, " +
//            "s.articlePart.id as articlePartId, " +
//            "s.articlePart.isDiscount as isDiscount, " +
//            "s.articlePart.isActive as isActive, " +
//            "s.articlePart.articleId as articleId " +
//            "FROM OrganRegisteredArticlePartSettings s " +
//            "WHERE organId = :organId AND departmentId IS NULL ")
//    List<OrganArticleSettingsProjection> findAllProjectionByOrganIdAndEmptyDepartment(Long organId);
//
//    List<OrganRegisteredArticlePartSettings> findAllByOrganIdAndDepartmentId(Long organ, Long department);
//
//    boolean existsByOrganIdAndDepartmentIdAndArticlePartId(Long organId,
//                                                           Long departmentId,
//                                                           Long articlePartId);

    @Modifying
    @Query("DELETE FROM OrganRegisteredArticlePartSettings " +
            "WHERE organ = :organ AND department = :department")
    void deleteAllByOrganAndDepartment(@Param("organ") Organ organ,
                                       @Param("department") Department department);

    @Modifying
    @Query("DELETE FROM OrganRegisteredArticlePartSettings " +
            "WHERE organ = :organ AND department is null")
    void deleteAllByOrganAndEmptyDepartment(@Param("organ") Organ organ);
}
