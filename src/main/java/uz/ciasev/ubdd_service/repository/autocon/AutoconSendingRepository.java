package uz.ciasev.ubdd_service.repository.autocon;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;;
import uz.ciasev.ubdd_service.entity.autocon.AutoconSending;
import uz.ciasev.ubdd_service.entity.dict.autocon.AutoconSendingStatusAlias;

import java.util.List;
import java.util.Optional;

public interface AutoconSendingRepository extends JpaRepository<AutoconSending, Long> {

//    @Query("SELECT pp.punishment " +
//            "FROM PenaltyPunishment pp " +
//            "   INNER JOIN Punishment pu " +
//            "   ON pu.id = pp.punishmentId " +
////            "   AND NOT EXISTS(SELECT 1 FROM AutoconSending au WHERE au.punishmentId = pu.id AND au.sentTime IS NOT NULL) " +
//            "   AND NOT EXISTS(SELECT 1 FROM AutoconSending au WHERE au.punishmentId = pu.id) " +
//            "   AND pu.statusId <> 12 " +
//            "   INNER JOIN Decision de " +
//            "   ON de.id = pu.decisionId " +
//            "   AND de.executionFromDate < :nowMinus30Days " +
//            "   INNER JOIN Resolution re " +
//            "   ON re.id = de.resolutionId " +
//            "   AND re.isActive = true " +
//            "   INNER JOIN AdmCase ac " +
//            "   ON ac.id = re.admCaseId " +
//            "   AND ac.organId = 12 OR EXISTS(SELECT 1 FROM CourtMaterialFields cmf WHERE cmf.resolutionId = re.id) " +
//            "GROUP BY pp.punishment ")
//    List<Punishment> punishmentsToSendToAutocon(@Param("nowMinus30Days") LocalDate nowMinus30Days);

    Optional<AutoconSending> findByPunishmentId(Long punishmentId);

//    @Query(value = "SELECT au.id " +
//            "FROM core_v0.autocon_sending au " +
//            "WHERE au.status IN :awaitStatuses " +
//            "ORDER BY au.edited_time DESC " +
//            "LIMIT :n "
//            , nativeQuery = true)
//    List<Long> findNextToSend(List<String> awaitStatuses, Long n);

    @Query(value = "SELECT au.id " +
            "FROM AutoconSending au " +
            "WHERE au.status IN :awaitStatuses ")
    List<Long> findNextToSend(List<AutoconSendingStatusAlias> awaitStatuses, Pageable pageable);

    @Query(value = "SELECT au.id " +
            "FROM AutoconSending au " +
            "WHERE au.status = :awaitStatus ")
    List<Long> findNextToSend(AutoconSendingStatusAlias awaitStatus, Pageable pageable);
}
