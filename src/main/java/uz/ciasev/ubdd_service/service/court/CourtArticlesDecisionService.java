package uz.ciasev.ubdd_service.service.court;

import uz.ciasev.ubdd_service.entity.court.CourtArticleResultDecision;

import java.util.List;

public interface CourtArticlesDecisionService {

    void saveAll(List<CourtArticleResultDecision> articles);
}
