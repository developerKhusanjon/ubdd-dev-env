package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.court.CourtFinalResultDecision;

import java.util.List;

public interface CourtFinalResultDecisionRepository extends JpaRepository<CourtFinalResultDecision, Long> {


    @Query("SELECT crd FROM CourtFinalResultDecision crd WHERE crd.caseId = :caseId")
    List<CourtFinalResultDecision> findByCaseId(@Param("caseId") Long caseId);
}
