package uz.ciasev.ubdd_service.repository.resolution.execution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.resolution.execution.ManualPayment;
import uz.ciasev.ubdd_service.entity.dict.resolution.ManualPaymentSourceTypeAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ManualPaymentRepository extends JpaRepository<ManualPayment, Long> {

    @Query("SELECT i.id " +
            "FROM ManualPayment i " +
            "WHERE i.compensation.decision.violator = :violator")
    Collection<Long> findCompensationInvoiceIdByViolator(Violator violator);

    @Query("SELECT i.id " +
            "FROM ManualPayment i " +
            "WHERE i.penaltyPunishment.punishment.decision.violator = :violator")
    Collection<Long> findPunishmentInvoiceIdByViolator(Violator violator);

    Collection<Long> findByDamageSettlementDetailId(Long damageSettlementDetailId);

    @Query("SELECT i.id " +
            "FROM ManualPayment i " +
            "WHERE i.penaltyPunishment.punishment.decision.violatorId = :violatorId")
    Collection<Long> findPunishmentInvoiceIdByViolator(Long violatorId);

    Optional<ManualPayment> findTopByIdInOrderByPaymentDateDesc(Collection<Long> ids);

    @Query("SELECT sum(p.amount) " +
            "FROM ManualPayment p " +
            "WHERE p.id IN :ids")
    Optional<Long> sumAmountByIds(Collection<Long> ids);

    @Query("SELECT DISTINCT(p.user) " +
            "FROM ManualPayment p " +
            "WHERE p.id IN :ids")
    List<User> findUniqueCreateUsersByIds(Collection<Long> ids);

    @Modifying
    void deleteAllByPenaltyPunishmentIdAndSourceTypeId(Long penaltyPunishmentId, Long sourceTypeId);

    boolean existsByPenaltyPunishmentIdAndSourceTypeId(Long penaltyPunishmentId, Long sourceTypeId);

    @Modifying
    @Query("DELETE FROM ManualPayment p " +
            "WHERE p.penaltyPunishmentId = :penaltyPunishmentId AND p.sourceTypeAlias = :sourceType ")
    void deleteAllByPenaltyPunishmentIdAndSourceTypeAlias(Long penaltyPunishmentId, ManualPaymentSourceTypeAlias sourceType);

    @Modifying
    @Query("DELETE FROM ManualPayment p " +
            "WHERE p.sourceTypeAlias = :sourceType " +
            "   AND p.penaltyPunishmentId IN (SELECT pp.id FROM PenaltyPunishment pp WHERE pp.punishmentId = :punishmentId) ")
    void deleteAllByPunishmentIdAndSourceTypeAlias(Long punishmentId, ManualPaymentSourceTypeAlias sourceType);

    @Query("SELECT count(p) " +
            "FROM ManualPayment p " +
            "WHERE p.penaltyPunishmentId = :penaltyPunishmentId AND p.sourceTypeAlias IN :sourceTypes")
    Long countByPenaltyPunishmentIdAndSourceTypeAliases(Long penaltyPunishmentId, List<ManualPaymentSourceTypeAlias> sourceTypes);

    @Query("SELECT count(p) " +
            "FROM ManualPayment p " +
            "WHERE p.penaltyPunishment.punishmentId = :punishmentId AND p.sourceTypeAlias IN :sourceTypes")
    Long countByPunishmentIdAndSourceTypeAliases(Long punishmentId, List<ManualPaymentSourceTypeAlias> sourceTypes);

    @Modifying
    @Query("DELETE FROM ManualPayment p " +
            "WHERE p.compensationId = :compensationId AND p.sourceTypeAlias = :sourceType ")
    void deleteAllByCompensationIdAndSourceTypeAlias(Long compensationId, ManualPaymentSourceTypeAlias sourceType);

    @Query("SELECT count(p) " +
            "FROM ManualPayment p " +
            "WHERE p.compensationId = :compensationId AND p.sourceTypeAlias IN :sourceTypes ")
    Long countByCompensationIdAndSourceTypeAliases(Long compensationId, List<ManualPaymentSourceTypeAlias> sourceTypes);
}
