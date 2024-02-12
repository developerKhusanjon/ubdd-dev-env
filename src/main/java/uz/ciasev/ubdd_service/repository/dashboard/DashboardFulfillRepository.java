package uz.ciasev.ubdd_service.repository.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.protocol.Protocol;

import java.time.LocalDateTime;

public interface DashboardFulfillRepository extends JpaRepository<Protocol, Long> {

    // PROTOCOL: dashboard_protocols
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_protocols; " +
            "CREATE TABLE core_v0.dashboard_protocols AS " +
            "WITH CTE AS ( " +
            "    SELECT date_trunc('month', created_time) created_time_m " +
            "         , id " +
            "         , organ_id " +
            "         , department_id " +
            "         , region_id " +
            "         , district_id " +
            "         , is_tablet " +
            "    FROM core_v0.protocol p " +
            "    WHERE NOT is_deleted " +
            "       AND created_time >= :fromDate AND created_time < :toDate " +
            ") " +
            "SELECT created_time_m monthDate " +
            ", COUNT(DISTINCT id) countValue " +
            ", organ_id organId " +
            ", department_id departmentId " +
            ", region_id regionId " +
            ", district_id districtId " +
            ", is_tablet isTablet " +
            "FROM CTE " +
            "GROUP BY created_time_m " +
            ", organ_id " +
            ", department_id " +
            ", region_id " +
            ", district_id " +
            ", is_tablet " +
            "ORDER BY created_time_m " +
            ";", nativeQuery = true)
    void protocolCount(@Param("fromDate") LocalDateTime from,
                       @Param("toDate") LocalDateTime to);

    // ADM-CASE: dashboard_adm_cases
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_adm_cases; " +
//            "CREATE TABLE core_v0.dashboard_adm_cases AS " +
//            "SELECT COUNT(DISTINCT id) countValue" +
//            ", date_trunc('month', created_time) monthDate " +
//            ", organ_id organId " +
//            ", department_id departmentId " +
//            ", region_id regionId " +
//            ", district_id districtId " +
//            "FROM core_v0.adm_case " +
//            "WHERE NOT is_deleted " +
//            "  AND created_time >= :fromDate AND created_time < :toDate " +
//            "GROUP BY date_trunc('month', created_time) " +
//            ",  organ_id " +
//            ", department_id " +
//            ", region_id " +
//            ", district_id " +
            ";", nativeQuery = true)
    void admCaseCount(@Param("fromDate") LocalDateTime from,
                      @Param("toDate") LocalDateTime to);

    // DECISION: dashboard_decisions
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_decisions; " +
//            "CREATE TABLE core_v0.dashboard_decisions AS " +
//            "SELECT COUNT(DISTINCT d.id) countValue " +
//            ", date_trunc('month', r.resolution_time) monthDate " +
//            ", ac.organ_id organId " +
//            ", ac.department_id departmentId " +
//            ", ac.region_id regionId " +
//            ", ac.district_id districtId " +
//            "FROM core_v0.decision d " +
//            "    INNER JOIN core_v0.resolution r " +
//            "    ON d.resolution_id = r.id " +
//            "    AND r.is_active " +
//            "    AND r.resolution_time >= :fromDate AND r.resolution_time < :toDate " +
//            "    INNER JOIN core_v0.adm_case ac " +
//            "    ON r.adm_case_id = ac.id " +
//            "GROUP BY date_trunc('month', r.resolution_time) " +
//            ",  ac.organ_id " +
//            ", ac.department_id " +
//            ", ac.region_id " +
//            ", ac.district_id " +
            ";", nativeQuery = true)
    void decisionCount(@Param("fromDate") LocalDateTime from,
                       @Param("toDate") LocalDateTime to);

    // VIOLATOR: dashboard_violators
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_violators; " +
//            "CREATE TABLE core_v0.dashboard_violators AS " +
//            "WITH CTE AS ( " +
//            "    SELECT id " +
//            "         , date_trunc('month', created_time) created_time_m " +
//            "         , organ_id " +
//            "         , department_id " +
//            "         , region_id " +
//            "         , district_id " +
//            "    FROM core_v0.adm_case " +
//            "    WHERE NOT is_deleted " +
//            "      AND created_time >= :fromDate AND created_time < :toDate " +
//            ") " +
//            "SELECT COUNT(DISTINCT v.id) countValue " +
//            "         , cte.created_time_m monthDate " +
//            "         , cte.organ_id organId " +
//            "         , cte.department_id departmentId " +
//            "         , cte.region_id regionId " +
//            "         , cte.district_id districtId " +
//            "FROM core_v0.violator v " +
//            "    INNER JOIN CTE cte" +
//            "    ON v.adm_case_id = cte.id " +
//            "GROUP BY cte.created_time_m " +
//            "         , cte.organ_id " +
//            "         , cte.department_id " +
//            "         , cte.region_id " +
//            "         , cte.district_id " +
            ";", nativeQuery = true)
    void violatorCount(@Param("fromDate") LocalDateTime from,
                       @Param("toDate") LocalDateTime to);

    // VIOLATOR BY PROTOCOL: dashboard_violators_protocol
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_violators_protocol; " +
            "CREATE TABLE core_v0.dashboard_violators_protocol AS " +
            "WITH CTE AS ( " +
            "    SELECT vd.violator_id id " +
            "         , date_trunc('month', p.created_time) created_time_m " +
            "         , p.organ_id " +
            "         , p.department_id " +
            "         , p.region_id " +
            "         , p.district_id " +
            "    FROM core_v0.protocol p " +
            "       INNER JOIN core_v0.violator_detail vd " +
            "       ON p.violator_detail_id = vd.id " +
            "    WHERE NOT p.is_deleted " +
            "      AND p.created_time >= :fromDate AND p.created_time < :toDate " +
            ") " +
            "SELECT COUNT(DISTINCT v.id) countValue " +
            "         , cte.created_time_m monthDate " +
            "         , cte.organ_id organId " +
            "         , cte.department_id departmentId " +
            "         , cte.region_id regionId " +
            "         , cte.district_id districtId " +
            "FROM core_v0.violator v " +
            "    INNER JOIN CTE cte" +
            "    ON v.id = cte.id " +
            "GROUP BY cte.created_time_m " +
            "         , cte.organ_id " +
            "         , cte.department_id " +
            "         , cte.region_id " +
            "         , cte.district_id " +
            ";", nativeQuery = true)
    void violatorCountByProtocol(@Param("fromDate") LocalDateTime from,
                       @Param("toDate") LocalDateTime to);

    // VICTIMS: dashboard_victims
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_victims; " +
//            "CREATE TABLE core_v0.dashboard_victims AS " +
//            "WITH CTE AS ( " +
//            "    SELECT id " +
//            "         , date_trunc('month', created_time) created_time_m " +
//            "         , organ_id " +
//            "         , department_id " +
//            "         , region_id " +
//            "         , district_id " +
//            "    FROM core_v0.adm_case " +
//            "    WHERE NOT is_deleted " +
//            "      AND created_time >= :fromDate AND created_time < :toDate " +
//            ") " +
//            "SELECT COUNT(DISTINCT v.id) countValue " +
//            "         , cte.created_time_m monthDate " +
//            "         , cte.organ_id organId " +
//            "         , cte.department_id departmentId " +
//            "         , cte.region_id regionId " +
//            "         , cte.district_id districtId " +
//            "FROM core_v0.victim v " +
//            "    INNER JOIN CTE cte" +
//            "    ON v.adm_case_id = cte.id " +
//            "GROUP BY cte.created_time_m " +
//            "         , cte.organ_id " +
//            "         , cte.department_id " +
//            "         , cte.region_id " +
//            "         , cte.district_id " +
            ";", nativeQuery = true)
    void victimCount(@Param("fromDate") LocalDateTime from,
                     @Param("toDate") LocalDateTime to);

    // VICTIMS BY PROTOCOL: dashboard_victims_protocol
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_victims_protocol; " +
            "CREATE TABLE core_v0.dashboard_victims_protocol AS " +
            "WITH CTE AS ( " +
            "    SELECT vd.victim_id id " +
            "         , date_trunc('month', p.created_time) created_time_m " +
            "         , p.organ_id " +
            "         , p.department_id " +
            "         , p.region_id " +
            "         , p.district_id " +
            "    FROM core_v0.protocol p " +
            "       INNER JOIN core_v0.victim_detail vd " +
            "       ON p.id = vd.protocol_id " +
            "    WHERE NOT p.is_deleted " +
            "      AND p.created_time >= :fromDate AND p.created_time < :toDate " +
            ") " +
            "SELECT COUNT(DISTINCT v.id) countValue " +
            "         , cte.created_time_m monthDate " +
            "         , cte.organ_id organId " +
            "         , cte.department_id departmentId " +
            "         , cte.region_id regionId " +
            "         , cte.district_id districtId " +
            "FROM core_v0.victim v " +
            "    INNER JOIN CTE cte" +
            "    ON v.id = cte.id " +
            "GROUP BY cte.created_time_m " +
            "         , cte.organ_id " +
            "         , cte.department_id " +
            "         , cte.region_id " +
            "         , cte.district_id " +
            ";", nativeQuery = true)
    void victimCountByProtocol(@Param("fromDate") LocalDateTime from,
                     @Param("toDate") LocalDateTime to);

    // ADM-CASE BY STATUS: dashboard_adm_cases_st
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_adm_cases_st; " +
            "CREATE TABLE core_v0.dashboard_adm_cases_st AS " +
            "WITH CTE AS ( " +
            "    SELECT ac.id " +
            "       , ac.adm_status_id " +
            "       , date_trunc('month', ac.created_time) created_time_m  " +
//            "       , organ_id " +
//            "       , department_id " +
//            "       , region_id " +
//            "       , district_id " +
            "       , pro.organ_id " +
            "       , pro.department_id " +
            "       , pro.region_id " +
            "       , pro.district_id " +
            "    FROM core_v0.adm_case ac " +
            "       INNER JOIN core_v0.violator vio " +
            "       ON vio.adm_case_id = ac.id " +
            "           AND NOT vio.is_archived " +
            "       INNER JOIN core_v0.violator_detail vd " +
            "       ON vd.violator_id = vio.id " +
            "       INNER JOIN core_v0.protocol pro " +
            "       ON pro.violator_detail_id = vd.id " +
            "           AND pro.is_main " +
            "    WHERE NOT ac.is_deleted " +
            "      AND ac.created_time >= :fromDate AND ac.created_time < :toDate " +
            ") " +
            "SELECT cte.adm_status_id admStatusId " +
            "   , st.alias admStatusAlias " +
            "   , cte.created_time_m monthDate " +
            "   , COUNT(DISTINCT cte.id) countValue " +
            "   , cte.organ_id organId " +
            "   , cte.department_id departmentId " +
            "   , cte.region_id regionId " +
            "   , cte.district_id districtId " +
            "FROM CTE cte " +
            "    INNER JOIN core_v0.d_adm_status st " +
            "    ON cte.adm_status_id = st.id " +
            "GROUP BY cte.adm_status_id, st.alias, cte.created_time_m " +
            "         , cte.organ_id " +
            "         , cte.department_id " +
            "         , cte.region_id " +
            "         , cte.district_id " +
            ";", nativeQuery = true)
    void admCaseCountByStatus(@Param("fromDate") LocalDateTime from,
                              @Param("toDate") LocalDateTime to);

    // DECISION BY STATUS: dashboard_decisions_st
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_decisions_st; " +
            "CREATE TABLE core_v0.dashboard_decisions_st AS " +
            "WITH CTE AS ( " +
            "    SELECT r.id " +
            "         , date_trunc('month', r.resolution_time) resolution_time_m " +
//            "         , ac.organ_id " +
//            "         , ac.department_id " +
//            "         , ac.region_id " +
//            "         , ac.district_id " +
            "         , r.organ_id " +
            "         , r.department_id " +
            "         , r.region_id " +
            "         , r.district_id " +
            "    FROM core_v0.resolution r " +
            "       INNER JOIN core_v0.adm_case ac" +
            "       ON r.adm_case_id = ac.id " +
            "    WHERE is_active " +
            "      AND resolution_time >= :fromDate AND resolution_time < :toDate " +
            ") " +
            "SELECT d.adm_status_id admStatusId " +
            "   , st.alias admStatusAlias " +
            "   , cte.resolution_time_m monthDate " +
            "   , COUNT(DISTINCT d.id) countValue " +
            "   , cte.organ_id organId " +
            "   , cte.department_id departmentId " +
            "   , cte.region_id regionId " +
            "   , cte.district_id districtId " +
            "FROM CTE cte " +
            "    INNER JOIN core_v0.decision d " +
            "    ON d.resolution_id = cte.id " +
            "    INNER JOIN core_v0.d_adm_status st " +
            "    ON d.adm_status_id = st.id " +
            "GROUP BY d.adm_status_id, st.alias, cte.resolution_time_m " +
            "         , cte.organ_id " +
            "         , cte.department_id " +
            "         , cte.region_id " +
            "         , cte.district_id " +
            ";", nativeQuery = true)
    void decisionCountByStatus(@Param("fromDate") LocalDateTime from,
                               @Param("toDate") LocalDateTime to);

    // TERMINATION: dashboard_terminations
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_terminations; " +
            "CREATE TABLE core_v0.dashboard_terminations AS " +
            "WITH CTE AS ( " +
            "    SELECT r.id " +
            "         , date_trunc('month', r.resolution_time) resolution_time_m " +
//            "         , ac.organ_id " +
//            "         , ac.department_id " +
//            "         , ac.region_id " +
//            "         , ac.district_id " +
            "         , r.organ_id " +
            "         , r.department_id " +
            "         , r.region_id " +
            "         , r.district_id " +
            "    FROM core_v0.resolution r " +
            "       INNER JOIN core_v0.adm_case ac" +
            "       ON r.adm_case_id = ac.id " +
            "    WHERE is_active " +
            "      AND resolution_time >= :fromDate AND resolution_time < :toDate " +
            ") " +
            "SELECT COUNT(CASE WHEN d.termination_reason_id NOT IN (1, 15) THEN d.id ELSE null END) countValue " +
            "         , COUNT(CASE WHEN d.termination_reason_id IN (1, 15) THEN d.id ELSE null END) countValue21 " +
            "         , cte.resolution_time_m monthDate " +
            "         , cte.organ_id organId " +
            "         , cte.department_id departmentId " +
            "         , cte.region_id regionId " +
            "         , cte.district_id districtId " +
            "FROM core_v0.decision d " +
            "    INNER JOIN CTE cte " +
            "    ON d.resolution_id = cte.id " +
            "WHERE d.decision_type_id = (SELECT id FROM core_v0.d_decision_type WHERE alias = 'TERMINATION') " +
            "GROUP BY cte.resolution_time_m " +
            "         , cte.organ_id " +
            "         , cte.department_id " +
            "         , cte.region_id " +
            "         , cte.district_id " +
            ";", nativeQuery = true)
    void terminationCount(@Param("fromDate") LocalDateTime from,
                          @Param("toDate") LocalDateTime to);

    // PENALTY CHART: dashboard_penalty_chart
    @Modifying
    @Query(value = "DROP INDEX IF EXISTS core_v0.dashboard_penalty_chart_idx; " +
            "DROP TABLE IF EXISTS core_v0.dashboard_penalty_chart; " +
            "CREATE TABLE core_v0.dashboard_penalty_chart AS " +
            "WITH CTE AS ( " +
            "    SELECT r.id " +
            "        , r.resolution_time " +
            "        , date_trunc('day', r.resolution_time) + interval '15' day resolution_date " +
            "        , date_trunc('month', r.resolution_time) resolution_time_m " +
//            "         , ac.organ_id " +
//            "         , ac.department_id " +
//            "         , ac.region_id " +
//            "         , ac.district_id " +
            "         , r.organ_id " +
            "         , r.department_id " +
            "         , r.region_id " +
            "         , r.district_id " +
            "    FROM core_v0.resolution r " +
            "       INNER JOIN core_v0.adm_case ac" +
            "       ON r.adm_case_id = ac.id " +
            "    WHERE is_active " +
            "      AND resolution_time >= :fromDate AND resolution_time < :toDate " +
            ") " +
            "SELECT cte.resolution_date         resolutionDate" +
            "     , cte.resolution_time_m         monthDate " +
            "     , date_trunc('day', pp.last_pay_time) <= cte.resolution_date isDiscountPayDate " +
            "     , SUM(pp.amount)                   amount " +
            "     , SUM(pp.paid_amount)              paidAmount " +
            "     , SUM(pp.discount_amount)          discountAmount " +
            "     , pp.is_discount              isDiscount " +
            "     , cte.organ_id organId " +
            "     , cte.department_id departmentId " +
            "     , cte.region_id regionId " +
            "     , cte.district_id districtId " +
            "FROM CTE cte " +
            "    INNER JOIN core_v0.decision d " +
            "        ON d.resolution_id = cte.id " +
            "    INNER JOIN core_v0.punishment pu " +
            "        ON pu.decision_id = d.id " +
            "    INNER JOIN core_v0.penalty_punishment pp " +
            "        ON pp.punishment_id = pu.id " +
            "GROUP BY cte.resolution_date, date_trunc('day', pp.last_pay_time) <= cte.resolution_date, pp.is_discount, cte.resolution_time_m " +
            "         , cte.organ_id " +
            "         , cte.department_id " +
            "         , cte.region_id " +
            "         , cte.district_id " +
            ";" +
            "CREATE INDEX dashboard_penalty_chart_idx " +
            "ON core_v0.dashboard_penalty_chart (monthDate, organId, departmentId, regionId, districtId) " +
            ";", nativeQuery = true)
    void penaltyChart(@Param("fromDate") LocalDateTime from,
                      @Param("toDate") LocalDateTime to);

    // MIB AMOUNT: dashboard_mib_amounts
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_mib_amounts; " +
            "CREATE TABLE core_v0.dashboard_mib_amounts AS " +
            "WITH CTE AS ( " +
            "    SELECT r.id " +
            "         , r.resolution_time " +
            "         , date_trunc('month', r.resolution_time) resolution_time_m " +
//            "         , ac.organ_id " +
//            "         , ac.department_id " +
//            "         , ac.region_id " +
//            "         , ac.district_id " +
            "         , r.organ_id " +
            "         , r.department_id " +
            "         , r.region_id " +
            "         , r.district_id " +
            "    FROM core_v0.resolution r " +
            "       INNER JOIN core_v0.adm_case ac" +
            "       ON r.adm_case_id = ac.id " +
            "    WHERE is_active " +
            "      AND resolution_time >= :fromDate AND resolution_time < :toDate " +
            ") " +
            "SELECT SUM(mov.amount_of_recovery) amount " +
            "     , cte.resolution_time_m monthDate " +
            "         , cte.organ_id organId " +
            "         , cte.department_id departmentId " +
            "         , cte.region_id regionId " +
            "         , cte.district_id districtId " +
            "FROM core_v0.mib_execution_card mec " +
            "    INNER JOIN core_v0.decision d " +
            "        ON d.id = mec.decision_id " +
            "        AND mec.mib_owner_type_id = (SELECT id FROM core_v0.mib_owner_type WHERE alias = 'DECISION') " +
            "    INNER JOIN CTE cte " +
            "        ON d.resolution_id = cte.id " +
            "    INNER JOIN core_v0.mib_card_movement mov " +
            "        ON mov.card_id = mec.id " +
            "           AND mov.is_active " +
            "GROUP BY cte.resolution_time_m " +
            "         , cte.organ_id " +
            "         , cte.department_id " +
            "         , cte.region_id " +
            "         , cte.district_id " +
            ";", nativeQuery = true)
    void mibAmountByMonth(@Param("fromDate") LocalDateTime from,
                          @Param("toDate") LocalDateTime to);

    // COURT AMOUNT: dashboard_court_amounts
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_court_amounts; " +
            "CREATE TABLE core_v0.dashboard_court_amounts AS " +
            "WITH CTE AS ( " +
            "    SELECT r.id " +
            "         , date_trunc('month', r.resolution_time) resolution_time_m " +
//            "         , ac.organ_id " +
//            "         , ac.department_id " +
//            "         , ac.region_id " +
//            "         , ac.district_id " +
            "         , r.organ_id " +
            "         , r.department_id " +
            "         , r.region_id " +
            "         , r.district_id " +
            "    FROM core_v0.resolution r " +
            "       INNER JOIN core_v0.adm_case ac" +
            "       ON r.adm_case_id = ac.id " +
            "    WHERE r.is_active " +
            "      AND r.resolution_time >= :fromDate AND r.resolution_time < :toDate " +
            "      AND r.organ_id = 20 " +
            ") " +
            "SELECT SUM(pp.amount) amount " +
            "         , cte.resolution_time_m monthDate " +
            "         , cte.organ_id organId " +
            "         , cte.department_id departmentId " +
            "         , cte.region_id regionId " +
            "         , cte.district_id districtId " +
            "FROM CTE cte " +
            "    INNER JOIN core_v0.decision d " +
            "        ON d.resolution_id = cte.id " +
            "    INNER JOIN core_v0.punishment pu " +
            "        ON pu.decision_id = d.id " +
            "    INNER JOIN core_v0.penalty_punishment pp " +
            "        ON pp.punishment_id = pu.id " +
            "GROUP BY cte.resolution_time_m " +
            "         , cte.organ_id " +
            "         , cte.department_id " +
            "         , cte.region_id " +
            "         , cte.district_id " +
            ";", nativeQuery = true)
    void courtAmountByMonth(@Param("fromDate") LocalDateTime from,
                            @Param("toDate") LocalDateTime to);

    // PROTOCOL REPEATABILITY: dashboard_protocol_repeatability
    @Modifying
    @Query(value = "DROP TABLE IF EXISTS core_v0.dashboard_protocol_repeatability; " +
            "CREATE TABLE core_v0.dashboard_protocol_repeatability AS " +
            "WITH CTE AS ( " +
            "    SELECT date_trunc('month', created_time) created_time_m " +
            "         , id " +
            "         , violator_detail_id " +
            "         , organ_id " +
            "         , department_id " +
            "         , region_id " +
            "         , district_id " +
            "         , article_id " +
            "         , article_violation_type_id " +
            "    FROM core_v0.protocol p " +
            "    WHERE NOT is_deleted " +
            "       AND created_time >= :fromDate AND created_time < :toDate " +
            ") " +
            "SELECT cte.created_time_m monthDate " +
            ", COUNT(DISTINCT cte.id) repeatCount " +
            ", cte.organ_id organId " +
            ", cte.department_id departmentId " +
            ", cte.region_id regionId " +
            ", cte.district_id districtId " +
            ", cte.article_id articleId " +
            ", cte.article_violation_type_id articleViolationTypeId " +
            ", vio.person_id personId " +
            "FROM CTE cte " +
            "   INNER JOIN core_v0.violator_detail vde " +
            "   ON vde.id = cte.violator_detail_id " +
            "   INNER JOIN core_v0.violator vio " +
            "   ON vio.id = vde.violator_id " +
            "GROUP BY cte.created_time_m " +
            ", cte.organ_id " +
            ", cte.department_id " +
            ", cte.region_id " +
            ", cte.district_id " +
            ", cte.article_id" +
            ", cte.article_violation_type_id " +
            ", vio.person_id " +
            "HAVING COUNT(DISTINCT cte.id) > 1 " +
            "ORDER BY cte.created_time_m " +
            ";", nativeQuery = true)
    void protocolRepeatability(@Param("fromDate") LocalDateTime from,
                               @Param("toDate") LocalDateTime to);
}
