package uz.ciasev.ubdd_service.repository.admcase;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.admcase.AdmCaseListProjection;
import uz.ciasev.ubdd_service.entity.dict.Organ;

import java.util.List;
import java.util.Optional;

public interface AdmCaseRepository extends JpaRepository<AdmCase, Long>, JpaSpecificationExecutor<AdmCase>, AdmCaseCustomRepository {

    @Query("SELECT ac " +
            " FROM Protocol p " +
            " LEFT JOIN ViolatorDetail vd ON p.violatorDetailId = vd.id " +
            " LEFT JOIN Violator v ON vd.violatorId = v.id " +
            " LEFT JOIN AdmCase ac ON v.admCaseId = ac.id " +
            "WHERE p.id = :id ")
    Optional<AdmCase> findByProtocolId(@Param("id") Long id);

    @Query("SELECT ac " +
            " FROM AdmCase ac " +
            "RIGHT JOIN Violator v ON ac.id = v.admCaseId " +
            "WHERE v.id = :id ")
    Optional<AdmCase> findByViolatorId(@Param("id") Long id);

    @Query("SELECT ac " +
            " FROM AdmCase ac " +
            "WHERE ac.isDeleted = FALSE " +
            "  AND ac.id = (SELECT v.admCaseId FROM Violator v " +
            "   WHERE v.id = (SELECT vd.violatorId FROM ViolatorDetail vd " +
            "       WHERE vd.id = (SELECT p.violatorDetailId FROM Protocol p WHERE p.juridicId = :juridicId)))")
//            "       WHERE vd.id = (SELECT p.violatorDetailId FROM Protocol p WHERE p.juridicId = :juridicId AND p.isDeleted = FALSE)))")
    Optional<AdmCase> findByJuridicId(@Param("juridicId") Long juridicId);

    @Query("SELECT ac " +
            " FROM AdmCase ac " +
            "WHERE ac.isDeleted = FALSE " +
            "  AND ac.id = :id " +
            "  AND ac.claimId = :claimId")
    Optional<AdmCase> findByIdAndClaimId(@Param("id") Long id,
                                         @Param("claimId") Long claimId);

    @Query("SELECT ac " +
            " FROM AdmCase ac " +
            "WHERE ac.claimId = :claimId")
    Optional<AdmCase> findByClaimId(@Param("claimId") Long claimMergeId);

    @Query("SELECT ac.id as id, " +
            "ac.createdTime as createdTime, " +
            "ac.editedTime as editedTime, " +
            "ac.userId as userId, " +
            "ac.statusId as statusId, " +
            "ac.series as series, " +
            "ac.number as number, " +
            "ac.openedDate as openedDate, " +
            "ac.consideredTime as consideredTime, " +
            "ac.considerInfo as considerInfo, " +
            "ac.isDeleted as isDeleted, " +
            "ac.considerUserId as considerUserId, " +
            "ac.organId as organId, " +
            "ac.departmentId as departmentId, " +
            "ac.regionId as regionId, " +
            "ac.districtId as districtId, " +
            "ac.mergedToAdmCaseId as mergedToAdmCaseId, " +
            "ac.claimId as claimId, " +
            "ac.courtOutNumber as courtOutNumber, " +
            "ac.courtOutDate as courtOutDate, " +
            "ac.courtRegionId as courtRegionId, " +
            "ac.courtDistrictId as courtDistrictId, " +
            "ac.courtConsideringBasisId as courtConsideringBasisId, " +
            "ac.courtConsideringAdditionId as courtConsideringAdditionId, " +

            "cf.statusId as courtStatusId, " +
            "cf.hearingDate as courtHearingDate, " +
            "cf.judge as judge, " +

            "get_person_by_case_id(ac.id) as violators, " +
            "get_protocol_article_parts_by_case_id(ac.id) as articleParts, " +
            "get_protocols_in_case_id(ac.id) as protocolCount " +

            " FROM AdmCase ac LEFT JOIN CourtCaseFields cf ON ac.id = cf.caseId " +
            "WHERE ac.id IN :ids ")
    List<AdmCaseListProjection> findListProjectionByIds(@Param("ids") Iterable<Long> ids, Sort sort);

    @Query("SELECT ac.organ FROM AdmCase ac WHERE ac.id = :id")
    Optional<Organ> findOrganByAdmCaseId(@Param("id") Long id);
}
