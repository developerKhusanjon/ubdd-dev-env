package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.court.BillingAction;
import uz.ciasev.ubdd_service.entity.court.BillingSending;

import java.util.List;

public interface BillingSendingRepository extends JpaRepository<BillingSending, Long> {

    @Query("SELECT bs " +
            " FROM BillingSending bs " +
            "WHERE bs.isSent = FALSE " +
            "  AND bs.action = :action")
    List<BillingSending> findUnsentInvoice(@Param("action") BillingAction action, Pageable pageable);

    @Query("SELECT bs " +
            " FROM BillingSending bs " +
            "WHERE bs.isSent = FALSE " +
            "ORDER BY bs.createdTime")
    List<BillingSending> findUnsentInvoice(Pageable pageable);
}
