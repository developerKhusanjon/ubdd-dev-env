package uz.ciasev.ubdd_service.service.court;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.court.CourtArticleResultDecision;
import uz.ciasev.ubdd_service.repository.court.CourtArticlesDecisionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtArticlesDecisionServiceImpl implements CourtArticlesDecisionService {

    private final CourtArticlesDecisionRepository courtArticlesDecisionRepository;

    @Override
    public void saveAll(List<CourtArticleResultDecision> articles) {
        courtArticlesDecisionRepository.saveAll(articles);
    }
}
