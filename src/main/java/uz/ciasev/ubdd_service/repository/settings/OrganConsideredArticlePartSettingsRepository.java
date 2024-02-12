package uz.ciasev.ubdd_service.repository.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.dict.Department;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.settings.OrganConsideredArticlePartSettings;


public interface OrganConsideredArticlePartSettingsRepository extends JpaRepository<OrganConsideredArticlePartSettings, Long>, OrganConsideredArticlePartSettingsCustomRepository, JpaSpecificationExecutor<OrganConsideredArticlePartSettings> {

//    @Query("SELECT " +
//            "s.id as id, " +
//            "s.articlePart.id as articlePartId, " +
//            "s.articlePart.isDiscount as isDiscount, " +
//            "s.articlePart.isActive as isActive, " +
//            "s.articlePart.articleId as articleId " +
//            "FROM OrganConsideredArticlePartSettings s " +
//            "WHERE s.organ = :organ " +
//            "   AND s.department = :department ")
//    List<OrganArticleSettingsProjection> findAllProjectionByOrganAndDepartment(Organ organ,
//                                                                               Department department);
//
//    @Query("SELECT " +
//            "s.id as id, " +
//            "s.articlePart.id as articlePartId, " +
//            "s.articlePart.isDiscount as isDiscount, " +
//            "s.articlePart.isActive as isActive, " +
//            "s.articlePart.articleId as articleId " +
//            "FROM OrganConsideredArticlePartSettings s " +
//            "WHERE s.organ = :organ " +
//            "   AND s.department IS NULL ")
//    List<OrganArticleSettingsProjection> findAllProjectionByOrganAndEmptyDepartment(Organ organ);
//
//    @Query("SELECT " +
//            "s.id as id, " +
//            "s.articlePart.id as articlePartId, " +
//            "s.articlePart.isDiscount as isDiscount, " +
//            "s.articlePart.isActive as isActive, " +
//            "s.articlePart.articleId as articleId " +
//            "FROM OrganConsideredArticlePartSettings s " +
//            "WHERE s.organ = :organ " +
//            "   AND s.department = :department " +
//            "   AND s.isHeaderOnly = :isHeaderOnly ")
//    List<OrganArticleSettingsProjection> findAllProjectionByOrganAndDepartmentAndIsHeaderOnly(Organ organ,
//                                                                                              Department department,
//                                                                                              Boolean isHeaderOnly);
//
//    @Query("SELECT " +
//            "s.id as id, " +
//            "s.articlePart.id as articlePartId, " +
//            "s.articlePart.isDiscount as isDiscount, " +
//            "s.articlePart.isActive as isActive, " +
//            "s.articlePart.articleId as articleId " +
//            "FROM OrganConsideredArticlePartSettings s " +
//            "WHERE s.organ = :organ " +
//            "   AND s.department IS NULL " +
//            "   AND s.isHeaderOnly = :isHeaderOnly ")
//    List<OrganArticleSettingsProjection> findAllProjectionByOrganAndEmptyDepartmentAndIsHeaderOnly(Organ organ,
//                                                                                                   Boolean isHeaderOnly);
//
//    List<OrganConsideredArticlePartSettings> findByOrganAndDepartmentAndIsHeaderOnly(Organ organ,
//                                                                                     Department department,
//                                                                                     Boolean isHeader);
//
//    List<OrganConsideredArticlePartSettings> findByOrganAndDepartment(Organ organ,
//                                                                      Department department);
//
//    List<OrganConsideredArticlePartSettings> findByArticlePart(ArticlePart articlePart);
//
//    boolean existsByArticlePartAndOrganAndDepartmentAndIsHeaderOnly(ArticlePart articlePart,
//                                                                    Organ organ,
//                                                                    Department department,
//                                                                    Boolean isHeader);
//
//    boolean existsByArticlePartAndOrganAndDepartment(ArticlePart articlePart,
//                                                     Organ organ,
//                                                     Department department);
//
//    List<OrganConsideredArticlePartSettings> findAllByOrganIdAndDepartmentId(Long organId, Long departmentId);

    @Modifying
    @Query("DELETE FROM OrganConsideredArticlePartSettings cp " +
            "WHERE cp.organ = :organ AND cp.department = :department")
    void deleteAllByOrganAndDepartment(@Param("organ") Organ organ,
                                       @Param("department") Department department);

    @Modifying
    @Query("DELETE FROM OrganConsideredArticlePartSettings cp " +
            "WHERE cp.organ = :organ AND cp.department is null")
    void deleteAllByOrganAndEmptyDepartment(@Param("organ") Organ organ);
}
