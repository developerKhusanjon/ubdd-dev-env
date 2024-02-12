package uz.ciasev.ubdd_service.dto.internal.request.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartViolationType;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class ArticlePartViolationTypeCreateRequestDTO extends ArticlePartViolationTypeUpdateRequestDTO {

    @NotNull(message = ErrorCode.ARTICLE_PART_REQUIRED)
    @JsonProperty(value = "articlePartId")
    private ArticlePart articlePart;

    @NotNull(message = ErrorCode.VIOLATION_TYPE_REQUIRED)
    @JsonProperty(value = "violationTypeId")
    private ArticleViolationType articleViolationType;

    public ArticlePartViolationType apply(ArticlePartViolationType articlePartViolationType) {
        super.apply(articlePartViolationType);
        articlePartViolationType.setArticlePart(this.articlePart);
        articlePartViolationType.setArticleViolationType(this.articleViolationType);
        return articlePartViolationType;
    }

    public ArticlePartViolationType build() {
        return apply(new ArticlePartViolationType());
    }
}
