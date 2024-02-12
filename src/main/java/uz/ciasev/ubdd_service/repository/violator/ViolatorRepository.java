package uz.ciasev.ubdd_service.repository.violator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.entity.violator.ViolatorCitizenListProjection;
import uz.ciasev.ubdd_service.entity.violator.ViolatorCourtReturnReasonProjection;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ViolatorRepository extends JpaRepository<Violator, Long>, JpaSpecificationExecutor<Violator>, ViolatorRepositoryCustom {

    @Modifying
    @Query(value = "UPDATE Violator v SET v.mergedToViolatorId = :toViolatorId WHERE v.id = :fromViolatorId")
    void mergedTo(@Param("fromViolatorId") Long fromViolatorId, @Param("toViolatorId") Long toViolatorId);

    @Modifying
    @Query(value = "UPDATE Violator v SET v.isArchived = :isArchived, v.archivedDate = :archivedDate WHERE v.id in :violatorsId")
    void setArchive(List<Long> violatorsId, Boolean isArchived, LocalDate archivedDate);

    @Modifying
    @Query(value = "UPDATE Violator v SET v.inn = :inn WHERE v.id = :violatorId")
    void setInn(Long violatorId, String inn);

    @Modifying
    @Query(value = "UPDATE Violator v SET v.photoUri = :photoUri WHERE v.id = :violatorId")
    void setPhotoUri(Long violatorId, String photoUri);

    @Query("SELECT v.id " +
            " FROM Violator v " +
            "WHERE v.admCaseId = :admCaseId " +
            "  AND v.personId = :personId")
    Optional<Long> findIdByAdmCaseAndPersonIds(@Param("admCaseId") Long admCaseId,
                                               @Param("personId") Long personId);


    Optional<Violator> findByAdmCaseIdAndPersonId(Long admCaseId, Long personId);

    List<Violator> findByAdmCaseId(@Param("admCaseId") Long admCaseId);

    @Query("SELECT " +
            "   DISTINCT (v.id) as id, " +
            "   v.person.pinpp as pinpp, " +
            "   v.admCase.number as admCaseNumber, " +
            "   v.admCase.openedDate as admCaseOpenedDate, " +
            "   v.admCase.consideredTime as admCaseConsideredTime, " +
            "   v.admCase.createdTime as admCaseCreatedTime, " +
            "   v.admCase.status as admCaseStatus, " +
            "   v.admCase.organ as admCaseOrgan, " +
            "   caseDepartment as admCaseDepartment, " +
            "   caseDistrict as admCaseDistrict, " +
            "   caseRegion as admCaseRegion, " +
            "   decisionStatus as decisionStatus " +
            "FROM Violator v " +
            "LEFT JOIN Resolution r ON r.admCase = v.admCase AND r.isActive = TRUE " +
            "LEFT JOIN Decision d ON d.violator = v AND d.resolution = r " +
            "LEFT JOIN  AdmStatus decisionStatus ON d.status = decisionStatus " +
            "LEFT JOIN  Region caseRegion ON v.admCase.region = caseRegion " +
            "LEFT JOIN  District caseDistrict ON v.admCase.district = caseDistrict " +
            "LEFT JOIN  Department caseDepartment ON v.admCase.department = caseDepartment " +
            "WHERE v.id IN :ids " +
            "ORDER BY v.admCase.openedDate DESC ")
    List<ViolatorCitizenListProjection> findAllViolatorCitizenListProjectionByIds(List<Long> ids);

    @Query("SELECT " +
            "   v.id as id, " +
            "   v.person.firstNameLat as firstNameLat, " +
            "   v.person.secondNameLat as secondNameLat, " +
            "   v.person.lastNameLat as lastNameLat, " +
            "   v.courtReturnReasonId as courtReturnReasonId " +
            "FROM Violator v " +
            "WHERE v.admCaseId = :admCaseId AND v.courtReturnReasonId IS NOT NULL ")
    List<ViolatorCourtReturnReasonProjection> findViolatorCourtReturnReasonProjectionByAdmCaseId(Long admCaseId);
}