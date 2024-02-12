package uz.ciasev.ubdd_service.repository.invoice;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findById(Long id);

    Optional<Invoice> findByInvoiceId(Long id);

    Optional<Invoice> findByPenaltyPunishmentId(Long penaltyId);

    boolean existsByPenaltyPunishmentId(Long penaltyId);

    List<Invoice> findByPenaltyPunishmentId(Long penaltyId, Pageable page);

    Optional<Invoice> findByCompensationId(Long compensationId);

    boolean existsByCompensationId(Long compensationId);

    @Query("SELECT i.penaltyPunishment.punishment.decision.resolution.admCase.organ FROM Invoice i WHERE i = :invoice")
    Optional<Organ> findAdmCaseOrganByInvoiceInPenaltyPunishment(@Param("invoice") Invoice invoice);

    @Query("SELECT i.compensation.decision.resolution.admCase.organ FROM Invoice i WHERE i = :invoice")
    Optional<Organ> findAdmCaseOrganByInvoiceInCompensation(@Param("invoice") Invoice invoice);

    @Query("SELECT i.penaltyPunishment.punishment.decision.resolution.admCase FROM Invoice i WHERE i = :invoice")
    Optional<AdmCase> findAdmCaseByInvoiceInPenaltyPunishment(@Param("invoice") Invoice invoice);

    @Query("SELECT i.compensation.decision.resolution.admCase FROM Invoice i WHERE i = :invoice")
    Optional<AdmCase> findAdmCaseByInvoiceInCompensation(@Param("invoice") Invoice invoice);

    @Query("SELECT i.penaltyPunishment.punishment.decision FROM Invoice i WHERE i = :invoice")
    Optional<Decision> findDecisionByInvoiceInPenaltyPunishment(@Param("invoice") Invoice invoice);

    @Query("SELECT i.compensation.decision FROM Invoice i WHERE i = :invoice")
    Optional<Decision> findDecisionByInvoiceInCompensation(@Param("invoice") Invoice invoice);

    Optional<Invoice> findByInvoiceSerial(String invoiceSerial);

    @Query("SELECT i.id " +
            "FROM Invoice i " +
            "WHERE i.compensation.decision.violator = :violator")
    Collection<Long> findCompensationInvoiceIdByViolator(Violator violator);

    @Query("SELECT i.id " +
            "FROM Invoice i " +
            "WHERE i.penaltyPunishment.punishment.decision.violator = :violator")
    Collection<Long> findPunishmentInvoiceIdByViolator(Violator violator);

    @Query("SELECT i.id " +
            "FROM Invoice i " +
            "WHERE i.damageSettlementDetailId = :damageSettlementDetailId")
    Collection<Long> findInvoiceIdByDamageSettlementDetailId(Long damageSettlementDetailId);

    @Query("SELECT i.id " +
            "FROM Invoice i " +
            "WHERE i.penaltyPunishment.punishment.decision.violatorId = :violatorId")
    Collection<Long> findPunishmentInvoiceIdByViolatorId(Long violatorId);

    @Query("SELECT i.penaltyPunishment.punishment.decision FROM Invoice i WHERE i.invoiceSerial = :invoiceSerial")
    Optional<Decision> findDecisionByPenaltyInvoiceSerial(String invoiceSerial);
}
