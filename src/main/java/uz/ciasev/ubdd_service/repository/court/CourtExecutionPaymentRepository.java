package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.court.CourtExecutionPayment;

import java.util.List;

public interface CourtExecutionPaymentRepository extends JpaRepository<CourtExecutionPayment, Long>, JpaSpecificationExecutor<CourtExecutionPayment> {

    @Query("SELECT cep FROM CourtExecutionPayment cep WHERE cep.isSent = FALSE ORDER BY cep.editedTime")
    List<CourtExecutionPayment> findAllUnsent();

//    @Query("SELECT cep FROM CourtExecutionPayment cep WHERE cep.isSent = FALSE AND cep.hasError = TRUE AND cep.error IS NULL AND cep.invoiceUrl = ''")
//    List<CourtExecutionPayment> findAllWithoutInvoiceUrl();

}
