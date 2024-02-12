package uz.ciasev.ubdd_service.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;

import java.time.LocalDateTime;

public interface DashboardVisibilityRepository extends JpaRepository<Protocol, Long> {

    // PROTOCOL IDS BY VISIBILITY
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS tmp_protocol_ids; " +
            "SELECT id, :userId " +
            "INTO TEMPORARY TABLE tmp_protocol_ids " +
            "FROM core_v0.protocol " +
            "WHERE NOT is_deleted " +
            "  AND created_time >= :fromDate AND created_time < :toDate " +
            "  AND (:organId = 0      OR organ_id = :organId ) " +
            "  AND (:departmentId = 0 OR department_id = :departmentId ) " +
            "  AND (:regionId = 0     OR region_id = :regionId ) " +
            "  AND (:districtId = 0   OR district_id = :districtId ) " +
            ";", nativeQuery = true)
    void protocolIds(@Param("userId") Long userId,
                    @Param("fromDate") LocalDateTime from,
                    @Param("toDate") LocalDateTime to,
                    @Param("organId") long organId,
                    @Param("departmentId") long departmentId,
                    @Param("regionId") long regionId,
                    @Param("districtId") long districtId);

    @Modifying
    @Query(value = "DROP TABLE IF EXISTS tmp_protocol_ids; ", nativeQuery = true)
    void dropProtocolIds();

    // ADM-CASE IDS BY VISIBILITY
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS tmp_adm_case_ids; " +
            "SELECT id, :userId " +
            "INTO TEMPORARY TABLE tmp_adm_case_ids " +
            "FROM core_v0.adm_case " +
            "WHERE NOT is_deleted " +
            "  AND created_time >= :fromDate AND created_time < :toDate " +
            "  AND (:organId = 0      OR organ_id = :organId ) " +
            "  AND (:departmentId = 0 OR department_id = :departmentId ) " +
            "  AND (:regionId = 0     OR region_id = :regionId ) " +
            "  AND (:districtId = 0   OR district_id = :districtId ) " +
            ";", nativeQuery = true)
    void admCaseIds(@Param("userId") Long userId,
                    @Param("fromDate") LocalDateTime from,
                    @Param("toDate") LocalDateTime to,
                    @Param("organId") Long organId,
                    @Param("departmentId") Long departmentId,
                    @Param("regionId") Long regionId,
                    @Param("districtId") Long districtId);

    @Modifying
    @Query(value = "DROP TABLE IF EXISTS tmp_adm_case_ids; ", nativeQuery = true)
    void dropAdmCaseIds();

    // ADM-CASE IDS BY VISIBILITY FOR RESOLUTION LIST
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS tmp_adm_case_res_ids; " +
            "SELECT r.adm_case_id id, :userId " +
            "INTO TEMPORARY TABLE tmp_adm_case_res_ids " +
            "FROM core_v0.resolution r " +
            "   INNER JOIN core_v0.adm_case ac " +
            "   ON r.adm_case_id = ac.id " +
            "     AND (:organId = 0      OR ac.organ_id = :organId ) " +
            "     AND (:departmentId = 0 OR ac.department_id = :departmentId ) " +
            "     AND (:regionId = 0     OR ac.region_id = :regionId ) " +
            "     AND (:districtId = 0   OR ac.district_id = :districtId ) " +
            "WHERE resolution_time >= :fromDate AND resolution_time < :toDate " +
            ";", nativeQuery = true)
    void admCaseIdsForResolution(@Param("userId") Long userId,
                                @Param("fromDate") LocalDateTime from,
                                @Param("toDate") LocalDateTime to,
                                @Param("organId") Long organId,
                                @Param("departmentId") Long departmentId,
                                @Param("regionId") Long regionId,
                                @Param("districtId") Long districtId);

    @Modifying
    @Query(value = "DROP TABLE IF EXISTS tmp_adm_case_res_ids; ", nativeQuery = true)
    void dropAdmCaseIdsForResolution();
}
