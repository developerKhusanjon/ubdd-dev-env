package uz.ciasev.ubdd_service.service.dict.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartPenaltyRange;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamNotPresent;
import uz.ciasev.ubdd_service.repository.dict.article.ArticlePartPenaltyRangeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticlePartPenaltyRangeServiceImpl implements ArticlePartPenaltyRangeService {

    private final ArticlePartPenaltyRangeRepository repository;

    @Transactional
    @Override
    public List<ArticlePartPenaltyRange> replaceAll(ArticlePart savedArticlePart, ArticlePartRequestDTO requestDTO) {
        repository.deleteAllByArticlePartId(savedArticlePart.getId());
        repository.flush();
        return repository.saveAll(
                requestDTO.getPenaltyRange().stream()
                        .map(dto -> dto.build(savedArticlePart))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<ArticlePartPenaltyRange> findByArticlePartId(Long id) {

        return repository.findAllByArticlePartId(id);
    }

    @Override
    public ArticlePartPenaltyRange findLast(Long articlePartId, LocalDate date) {
        return repository.findTop1ByArticlePartIdAndFromDateLessThanEqualOrderByFromDateDesc(articlePartId, date)
                .orElseThrow(() -> new EntityByParamNotPresent(ArticlePartPenaltyRange.class, "articlePartId", articlePartId, "date", articlePartId));
    }
}
