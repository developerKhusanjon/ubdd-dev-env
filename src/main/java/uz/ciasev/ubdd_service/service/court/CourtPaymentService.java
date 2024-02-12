package uz.ciasev.ubdd_service.service.court;

import uz.ciasev.ubdd_service.entity.court.CourtExecutionPayment;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.invoice.Payment;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;

import java.util.List;

public interface CourtPaymentService {

    void acceptIfCourt(Invoice invoice, Payment payment);

    void accept(Decision decision, Invoice invoice, Payment payment);

    void send(CourtExecutionPayment courtExecutionPayment);

    List<CourtExecutionPayment> findAllUnsent();

    List<CourtExecutionPayment> findAllRejected();

    CourtExecutionPayment findById(Long id);
}
