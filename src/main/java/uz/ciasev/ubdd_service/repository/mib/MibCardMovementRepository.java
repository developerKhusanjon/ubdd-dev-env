package uz.ciasev.ubdd_service.repository.mib;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.entity.mib.MibCardMovement;
import uz.ciasev.ubdd_service.entity.mib.MibExecutionCard;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface MibCardMovementRepository extends JpaRepository<MibCardMovement, Long>, JpaSpecificationExecutor<MibCardMovement> {

    @Modifying
    @Query("UPDATE MibCardMovement m " +
            "SET m.isActive = FALSE " +
            "WHERE m.card = :card ")
    void setActiveToFalseForAllByCardId(@NotNull MibExecutionCard card);

    @Modifying
    @Query("UPDATE MibCardMovement m " +
            "SET m.isSynced = TRUE, m.syncEnvelopeId = :envelopeId, m.syncTime = now() " +
            "WHERE m.mibRequestId = :mibRequestId ")
    void setSyncByMibRequestId(@NotNull Long mibRequestId, @NotNull Long envelopeId);

    @Query("SELECT m " +
            "FROM MibCardMovement m " +
            "WHERE m.card.decisionId = :decisionId")
    List<MibCardMovement> findAllByDecisionId(@NotNull Long decisionId, Sort sort);

    List<MibCardMovement> findAllByCardId(@NotNull Long mibExecutionCardId, Sort sort);

    Optional<MibCardMovement> findByMibRequestId(@NotNull Long requestId);

    boolean existsByMibRequestId(@NotNull Long requestId);

    @Query(value = "SELECT * FROM core_v0.mib_card_movement cm " +
            "WHERE cm.is_court AND NOT cm.is_subscribed_court_movement " +
            "ORDER BY cm.created_time DESC " +
            "LIMIT 5000 " +
            ";"
            , nativeQuery = true)
    List<MibCardMovement> findAllUnsubscribedFromCourt();


    @Query("SELECT m " +
            "FROM MibCardMovement m " +
            "WHERE m.card = :card AND m.isActive = TRUE")
    Optional<MibCardMovement> getActiveByCard(@NotNull MibExecutionCard card);

    boolean existsByCardId(@NotNull Long cardId);
}
