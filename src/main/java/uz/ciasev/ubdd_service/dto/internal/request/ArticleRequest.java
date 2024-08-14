package uz.ciasev.ubdd_service.dto.internal.request;

import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;

public interface ArticleRequest {

    ArticlePart getArticlePart();

    ArticleViolationType getArticleViolationType();
}
