package uz.ciasev.ubdd_service.repository.court;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.ciasev.ubdd_service.entity.court.CourtArticleResultDecision;

public interface CourtArticlesDecisionRepository extends JpaRepository<CourtArticleResultDecision, Long> {
}
