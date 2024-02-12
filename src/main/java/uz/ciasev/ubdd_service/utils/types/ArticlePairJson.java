package uz.ciasev.ubdd_service.utils.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePairJson implements ArticlePair, Serializable {

    private Long articleId;
    private Long articlePartId;
    private Long articleViolationTypeId;

    public ArticlePairJson(ArticlePart articlePart, ArticleViolationType articleViolationType) {
        this.articleId = articlePart.getArticleId();
        this.articlePartId = articlePart.getId();
        this.articleViolationTypeId = Objects.nonNull(articleViolationType) ? articleViolationType.getId() : null;
    }
}



