package uz.ciasev.ubdd_service.dto.internal.request;

import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.utils.validator.ConsistArticle;

@ConsistArticle
public interface ArticleRequest {

    ArticlePart getArticlePart();

    ArticleViolationType getArticleViolationType();
}
