package uz.ciasev.ubdd_service.repository.resolution.punishment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface PunishmentRepository extends JpaRepository<Punishment, Long>, JpaSpecificationExecutor<Punishment> {

//    @Query("SELECT DISTINCT p.penalty " +
//            "FROM Punishment p " +
//            "WHERE p.type.alias = 'PENALTY' " +
//            "   AND p.decision.resolutionId = :resolutionId")
//    List<PenaltyPunishment> findByResolutionId(Long resolutionId);

    @Query("SELECT p.punishment " +
            "FROM PenaltyPunishment p " +
            "WHERE p.id = :id ")
    Optional<Punishment> findByPenaltyId(Long id);

    @Query("SELECT p.decision.violator " +
            "FROM Punishment p " +
            "WHERE p.id = :id ")
    Optional<Violator> findViolatorByPunishmentId(Long id);

    @Query("SELECT p.decision " +
            "FROM Punishment p " +
            "WHERE p.id = :id ")
    Optional<Decision> findDecisionByPunishmentId(Long id);

    @Query("SELECT p.decision.violatorId " +
            "FROM Punishment p " +
            "WHERE p.id = :id ")
    Long findViolatorIdByPunishmentId(Long id);

    @Query("SELECT p.punishment.decision.violator " +
            "FROM PenaltyPunishment p " +
            "WHERE p.id = :id ")
    Optional<Violator> findViolatorByPenaltyId(Long id);

    @Query("SELECT p.decision.resolution.admCase.organ " +
            "FROM Punishment p " +
            "WHERE p = :punishment ")
    Organ findAdmCaseOrganByPunishment(Punishment punishment);

    @Query("SELECT p.decision.resolution.admCase " +
            "FROM Punishment p " +
            "WHERE p = :punishment ")
    AdmCase findAdmCaseByPunishment(Punishment punishment);

    @Query("SELECT p.decision.resolution.admCase.id " +
            "FROM Punishment p " +
            "WHERE p = :punishment ")
    Long findAdmCaseIdByPunishment(Punishment punishment);

    @Query("SELECT p.decision.violator.id " +
            "FROM Punishment p " +
            "WHERE p = :punishment ")
    Long findViolatorIdByPunishment(Punishment punishment);

    @Query("SELECT pp.punishmentId " +
            "FROM PenaltyPunishment pp " +
            "WHERE pp.punishment.statusId <> 12 " +
            "   AND pp.punishment.decision.resolution.isActive = true " +
            "   AND pp.punishment.decision.executionFromDate < :nowMinus30Days " +
            "   AND pp.punishment.decision.resolution.admCase.organId = 12 " +
            "   AND NOT EXISTS(SELECT 1 FROM AutoconSending au WHERE au.punishmentId = pp.punishmentId) " +
            "   AND (" +
            "       pp.punishment.decision.resolution.organId = 12 " +
            "       OR EXISTS(SELECT 1 FROM CourtMaterialFields cmf WHERE cmf.resolutionId = pp.punishment.decision.resolutionId) " +
            "   ) ")
    List<Long> punishmentsToSendToAutocon(@Param("nowMinus30Days") LocalDate nowMinus30Days, Pageable pageable);


    @Modifying
    @Query(value = "update core_v0.punishment d set need_status_sync = false " +
            "where d.id = :id ", nativeQuery = true)
    void setStatusSynced(Long id);

    @Query(value = "select d.id " +
            "from core_v0.punishment d " +
            "where d.need_status_sync = True " +
            "limit :n ", nativeQuery = true)
    List<Long> getNextNForStatusSync(Long n);
}
