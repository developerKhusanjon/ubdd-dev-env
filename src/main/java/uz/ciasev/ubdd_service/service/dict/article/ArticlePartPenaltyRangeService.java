package uz.ciasev.ubdd_service.service.dict.article;

import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartPenaltyRange;

import java.time.LocalDate;
import java.util.List;

public interface ArticlePartPenaltyRangeService {

    List<ArticlePartPenaltyRange> replaceAll(ArticlePart savedArticlePart, ArticlePartRequestDTO requestDTO);
    List<ArticlePartPenaltyRange> findByArticlePartId(Long id);
    ArticlePartPenaltyRange findLast(Long articlePartId, LocalDate date);
}
