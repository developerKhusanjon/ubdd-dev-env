package uz.ciasev.ubdd_service.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;
import uz.ciasev.ubdd_service.repository.dashboard.projections.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DashboardRepository extends JpaRepository<Protocol, Long> {

    // PROTOCOL
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.countValue) countValue " +
            "FROM (SELECT * FROM core_v0.dashboard_protocols UNION ALL SELECT * FROM core_v0.dashboard_protocols_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            ";", nativeQuery = true)
    Optional<Long> protocolCount(@Param("fromDate") LocalDateTime from,
                                 @Param("toDate") LocalDateTime to,
                                 @Param("userId") Long userId,
                                 @Param("organId") Long organId,
                                 @Param("regionId") Long regionId,
                                 @Param("districtId") Long districtId);

    // PROTOCOL BY MONTH
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.countValue) countValue" +
            ", d.monthDate monthDate " +
            "FROM (SELECT * FROM core_v0.dashboard_protocols UNION ALL SELECT * FROM core_v0.dashboard_protocols_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            "GROUP BY d.monthDate " +
            ";", nativeQuery = true)
    List<ProtocolCountByMonthProjection> protocolCountByMonth(@Param("fromDate") LocalDateTime from,
                                                              @Param("toDate") LocalDateTime to,
                                                              @Param("userId") Long userId,
                                                              @Param("organId") Long organId,
                                                              @Param("regionId") Long regionId,
                                                              @Param("districtId") Long districtId);

    // PROTOCOL BY ORGAN
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.countValue) countValue" +
            ", d.organId organId " +
            "FROM (SELECT * FROM core_v0.dashboard_protocols UNION ALL SELECT * FROM core_v0.dashboard_protocols_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            "GROUP BY d.organId " +
            ";", nativeQuery = true)
    List<ProtocolCountByOrganProjection> protocolCountByOrgan(@Param("fromDate") LocalDateTime from,
                                                              @Param("toDate") LocalDateTime to,
                                                              @Param("userId") Long userId,
                                                              @Param("organId") Long organId,
                                                              @Param("regionId") Long regionId,
                                                              @Param("districtId") Long districtId);

    // PROTOCOL BY GEOGRAPHY
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.countValue) countValue" +
            ", d.regionId regionId " +
            ", d.districtId districtId " +
            "FROM (SELECT * FROM core_v0.dashboard_protocols UNION ALL SELECT * FROM core_v0.dashboard_protocols_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            "GROUP BY d.regionId, d.districtId " +
            ";", nativeQuery = true)
    List<ProtocolCountByGeographyProjection> protocolCountByGeography(@Param("fromDate") LocalDateTime from,
                                                                      @Param("toDate") LocalDateTime to,
                                                                      @Param("userId") Long userId,
                                                                      @Param("organId") Long organId,
                                                                      @Param("regionId") Long regionId,
                                                                      @Param("districtId") Long districtId);

    // ADM-CASE
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.countValue) countValue " +
            "FROM (SELECT * FROM core_v0.dashboard_adm_cases_st UNION ALL SELECT * FROM core_v0.dashboard_adm_cases_st_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            ";", nativeQuery = true)
    Optional<Long> admCaseCount(@Param("fromDate") LocalDateTime from,
                                @Param("toDate") LocalDateTime to,
                                @Param("userId") Long userId,
                                @Param("organId") Long organId,
                                @Param("regionId") Long regionId,
                                @Param("districtId") Long districtId);

    // DECISION
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.countValue) countValue " +
            "FROM (SELECT * FROM core_v0.dashboard_decisions_st UNION ALL SELECT * FROM core_v0.dashboard_decisions_st_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            ";", nativeQuery = true)
    Optional<Long> decisionCount(@Param("fromDate") LocalDateTime from,
                                 @Param("toDate") LocalDateTime to,
                                 @Param("userId") Long userId,
                                 @Param("organId") Long organId,
                                 @Param("regionId") Long regionId,
                                 @Param("districtId") Long districtId);

    // VIOLATOR
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.countValue) countValue " +
            "FROM (SELECT * FROM core_v0.dashboard_violators_protocol UNION ALL SELECT * FROM core_v0.dashboard_violators_protocol_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            ";", nativeQuery = true)
    Optional<Long> violatorCount(@Param("fromDate") LocalDateTime from,
                                 @Param("toDate") LocalDateTime to,
                                 @Param("userId") Long userId,
                                 @Param("organId") Long organId,
                                 @Param("regionId") Long regionId,
                                 @Param("districtId") Long districtId);

    // VICTIM
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.countValue) countValue " +
            "FROM (SELECT * FROM core_v0.dashboard_victims_protocol UNION ALL SELECT * FROM core_v0.dashboard_victims_protocol_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            ";", nativeQuery = true)
    Optional<Long> victimCount(@Param("fromDate") LocalDateTime from,
                               @Param("toDate") LocalDateTime to,
                               @Param("userId") Long userId,
                               @Param("organId") Long organId,
                               @Param("regionId") Long regionId,
                               @Param("districtId") Long districtId);

    // ADM-CASE BY STATUS
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT d.admStatusId admStatusId " +
            ", d.admStatusAlias admStatusAlias " +
            ", SUM(d.countValue) countValue " +
            "FROM (SELECT * FROM core_v0.dashboard_adm_cases_st UNION ALL SELECT * FROM core_v0.dashboard_adm_cases_st_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            "GROUP BY d.admStatusId, d.admStatusAlias " +
            ";", nativeQuery = true)
    List<CountByAdmStatusProjection> admCaseCountByStatus(@Param("fromDate") LocalDateTime from,
                                                          @Param("toDate") LocalDateTime to,
                                                          @Param("userId") Long userId,
                                                          @Param("organId") Long organId,
                                                          @Param("regionId") Long regionId,
                                                          @Param("districtId") Long districtId);

    // DECISION BY STATUS
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT d.admStatusId admStatusId " +
            ", d.admStatusAlias admStatusAlias " +
            ", SUM(d.countValue) countValue " +
            "FROM (SELECT * FROM core_v0.dashboard_decisions_st UNION ALL SELECT * FROM core_v0.dashboard_decisions_st_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            "GROUP BY d.admStatusId, d.admStatusAlias " +
            ";", nativeQuery = true)
    List<CountByAdmStatusProjection> decisionCountByStatus(@Param("fromDate") LocalDateTime from,
                                                           @Param("toDate") LocalDateTime to,
                                                           @Param("userId") Long userId,
                                                           @Param("organId") Long organId,
                                                           @Param("regionId") Long regionId,
                                                           @Param("districtId") Long districtId);

    // TERMINATION
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.countValue) countValue " +
            "   , SUM(d.countValue21) countValue21 " +
            "FROM (SELECT * FROM core_v0.dashboard_terminations UNION ALL SELECT * FROM core_v0.dashboard_terminations_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            ";", nativeQuery = true)
    TerminationProjection terminationCount(@Param("fromDate") LocalDateTime from,
                                           @Param("toDate") LocalDateTime to,
                                           @Param("userId") Long userId,
                                           @Param("organId") Long organId,
                                           @Param("regionId") Long regionId,
                                           @Param("districtId") Long districtId);

    // PENALTY CHART
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT d.monthDate monthDate " +
            "     , d.isDiscountPayDate isDiscountPayDate " +
            "     , SUM(d.amount) amount " +
            "     , SUM(d.paidAmount) paidAmount " +
            "     , SUM(d.discountAmount) discountAmount " +
            "     , d.isDiscount isDiscount " +
            "FROM (SELECT * FROM core_v0.dashboard_penalty_chart UNION ALL SELECT * FROM core_v0.dashboard_penalty_chart_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            "GROUP BY d.monthDate, d.isDiscountPayDate, d.isDiscount " +
            ";", nativeQuery = true)
    List<PenaltyChartProjection> penaltyChart(@Param("fromDate") LocalDateTime from,
                                              @Param("toDate") LocalDateTime to,
                                              @Param("userId") Long userId,
                                              @Param("organId") Long organId,
                                              @Param("regionId") Long regionId,
                                              @Param("districtId") Long districtId);

    // MIB AMOUNT BY MONTH
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.amount) amount " +
            "     , d.monthDate monthDate " +
            "FROM (SELECT * FROM core_v0.dashboard_mib_amounts UNION ALL SELECT * FROM core_v0.dashboard_mib_amounts_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            "GROUP BY d.monthDate " +
            ";", nativeQuery = true)
    List<AmountByMonthProjection> mibAmountByMonth(@Param("fromDate") LocalDateTime from,
                                                   @Param("toDate") LocalDateTime to,
                                                   @Param("userId") Long userId,
                                                   @Param("organId") Long organId,
                                                   @Param("regionId") Long regionId,
                                                   @Param("districtId") Long districtId);

    // COUNT AMOUNT BY MONTH
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.amount) amount" +
            ", d.monthDate monthDate " +
            "FROM (SELECT * FROM core_v0.dashboard_court_amounts UNION ALL SELECT * FROM core_v0.dashboard_court_amounts_custom) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            "GROUP BY d.monthDate " +
            ";", nativeQuery = true)
    List<AmountByMonthProjection> courtAmountByMonth(@Param("fromDate") LocalDateTime from,
                                                     @Param("toDate") LocalDateTime to,
                                                     @Param("userId") Long userId,
                                                     @Param("organId") Long organId,
                                                     @Param("regionId") Long regionId,
                                                     @Param("districtId") Long districtId);

    // PROTOCOL COUNT TABLET OR DESKTOP
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(CASE WHEN d.is_tablet_nn THEN d.countValue ELSE 0 END) countTablet " +
            "   , SUM(CASE WHEN NOT d.is_tablet_nn THEN d.countValue ELSE 0 END) countDesktop " +
            "FROM (" +
            "       SELECT *, coalesce(isTablet, false) is_tablet_nn FROM core_v0.dashboard_protocols " +
            "       UNION ALL " +
            "       SELECT *, coalesce(isTablet, false) is_tablet_nn FROM core_v0.dashboard_protocols_custom " +
            "   ) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            ";", nativeQuery = true)
    ProtocolCountByDevice protocolCountByDevice(@Param("fromDate") LocalDateTime from,
                                                @Param("toDate") LocalDateTime to,
                                                @Param("userId") Long userId,
                                                @Param("organId") Long organId,
                                                @Param("regionId") Long regionId,
                                                @Param("districtId") Long districtId);

    // PROTOCOL REPEATABILITY BY ORGAN
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT SUM(d.repeatCount) countValue " +
            "   , d.organId organId " +
            "FROM (" +
            "       SELECT * FROM core_v0.dashboard_protocol_repeatability " +
            "       UNION ALL " +
            "       SELECT * FROM core_v0.dashboard_protocol_repeatability_custom " +
            "   ) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            "GROUP BY d.organId " +
            ";", nativeQuery = true)
    List<RepeatabilityByOrganProjection> protocolRepeatabilityByOrgan(@Param("fromDate") LocalDateTime from,
                                                                      @Param("toDate") LocalDateTime to,
                                                                      @Param("userId") Long userId,
                                                                      @Param("organId") Long organId,
                                                                      @Param("regionId") Long regionId,
                                                                      @Param("districtId") Long districtId);

    // PROTOCOL REPEATABILITY BY REPEAT COUNT
    @Query(value = "WITH CTE AS ( " +
            "SELECT coalesce(organ_id, 0) organ_id " +
            ", coalesce(department_id, 0) department_id " +
            ", coalesce(region_id, 0) region_id " +
            ", coalesce(district_id, 0) district_id " +
            "FROM core_v0.users " +
            "WHERE id = :userId " +
            ")" +
            "SELECT d.repeatCount repeatCount " +
            "   , COUNT(d.*) countValue " +
            "FROM (" +
            "       SELECT * FROM core_v0.dashboard_protocol_repeatability " +
            "       UNION ALL " +
            "       SELECT * FROM core_v0.dashboard_protocol_repeatability_custom " +
            "   ) d " +
            "   INNER JOIN CTE cte " +
            "   ON (cte.organ_id = 0       OR cte.organ_id = d.organId) " +
            "   AND (cte.department_id = 0 OR cte.department_id = d.departmentId ) " +
            "   AND (cte.region_id = 0     OR cte.region_id = d.regionId ) " +
            "   AND (cte.district_id = 0   OR cte.district_id = d.districtId ) " +
            "WHERE d.monthDate >= :fromDate AND d.monthDate < :toDate " +
            "   AND (:organId = 0       OR :organId = d.organId) " +
            "   AND (:regionId = 0      OR :regionId = d.regionId ) " +
            "   AND (:districtId = 0    OR :districtId = d.districtId ) " +
            "GROUP BY d.repeatCount " +
            ";", nativeQuery = true)
    List<RepeatabilityByCountProjection> protocolRepeatabilityByCount(@Param("fromDate") LocalDateTime from,
                                                                      @Param("toDate") LocalDateTime to,
                                                                      @Param("userId") Long userId,
                                                                      @Param("organId") Long organId,
                                                                      @Param("regionId") Long regionId,
                                                                      @Param("districtId") Long districtId);
}
