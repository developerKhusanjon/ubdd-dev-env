package uz.ciasev.ubdd_service.repository.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.entity.invoice.PaymentDataProjection;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findById(Long id);

    Optional<Payment> findTopByInvoiceIdInOrderByPaymentTimeDesc(Collection<Long> invoicesId);

    @Query("SELECT sum(p.amount) " +
            "FROM Payment p " +
            "WHERE p.invoiceId IN :invoicesId")
    Optional<Long> sumAmountByInvoicesId(Collection<Long> invoicesId);

    List<Payment> findByInvoiceId(Long invoiceId);

    boolean existsByBid(String bid);

    @Query("SELECT p.id as id," +
            "p.amount as amount, " +
            "p.number as number, " +
            "p.paymentTime as paymentTime, " +
            "p.bid as bid, " +
            "p.blankNumber as blankNumber, " +
            "p.blankDate as blankDate," +
            "p.fromBankCode as fromBankCode, " +
            "p.fromBankAccount as fromBankAccount, " +
            "p.fromBankName as fromBankName, " +
            "p.fromInn as fromInn, " +
            "p.toBankCode as toBankCode, " +
            "p.toBankAccount as toBankAccount, " +
            "p.toBankName as toBankName, " +
            "p.toInn as toInn, " +
            "p.invoice.invoiceSerial as invoiceSerial " +
            "FROM Payment p " +
            "WHERE p.invoiceId IN :invoicesId " +
            "ORDER BY p.paymentTime DESC")

    List<PaymentDataProjection> findPaymentDataProjectionByInvoices(Collection<Long> invoicesId);

    Optional<Payment> findByBid(String bid);

    Optional<Payment> findByNumber(String number);
}
