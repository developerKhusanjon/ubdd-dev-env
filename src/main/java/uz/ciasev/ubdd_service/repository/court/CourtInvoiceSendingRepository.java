package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.court.CourtInvoiceSending;
import uz.ciasev.ubdd_service.entity.court.CourtInvoiceType;

import java.util.List;

public interface CourtInvoiceSendingRepository extends JpaRepository<CourtInvoiceSending, Long> {

    @Query("SELECT cis " +
            " FROM CourtInvoiceSending cis " +
            "WHERE cis.isSent = FALSE " +
            "  AND cis.type = :type ")
    List<CourtInvoiceSending> findUnsentInvoice(CourtInvoiceType type, Pageable pageable);
}
