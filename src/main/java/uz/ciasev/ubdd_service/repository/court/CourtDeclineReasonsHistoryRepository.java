package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import uz.ciasev.ubdd_service.entity.court.CourtDeclineReasonsHistory;

import java.util.List;

public interface CourtDeclineReasonsHistoryRepository extends JpaRepository<CourtDeclineReasonsHistory, Long> {

    List<CourtDeclineReasonsHistory> findAllByCaseIdOrderByCreatedTime(@Param("caseId") Long caseId);
}
