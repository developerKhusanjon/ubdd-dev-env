package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.court.CourtRequestOrder;

import java.util.List;

public interface CourtRequestOrderRepository extends JpaRepository<CourtRequestOrder, Long> {


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    default CourtRequestOrder create(CourtRequestOrder o) {
        return this.save(o);
    }

    boolean existsByMethodAndCaseIdAndClaimId(Integer method, Long caseId, Long claimId);

    @Modifying
    @Query("DELETE FROM CourtRequestOrder o WHERE o.method = :method AND o.caseId = :caseId AND o.claimId = :claimId")
    void deleteByCaseIdAndClaimId(Integer method, Long caseId, Long claimId);

    @Query("SELECT o FROM CourtRequestOrder o WHERE o.method = :method AND o.caseId = :caseId AND o.claimId < :claimId")
    List<CourtRequestOrder> getQueue(Integer method, Long caseId, Long claimId);

}
