package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.ArticleRequest;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.types.ArticlePairJson;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ConsistArticle;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"articlePart", "articleViolationType"})
@ConsistArticle
public class QualificationArticleRequestDTO implements ArticleRequest {

    @NotNull(message = ErrorCode.ARTICLE_PART_REQUIRED)
    @ActiveOnly(message = ErrorCode.ARTICLE_PART_DEACTIVATED)
    @JsonProperty(value = "articlePartId")
    private ArticlePart articlePart;

    @ActiveOnly(message = ErrorCode.ARTICLE_VIOLATION_TYPE_DEACTIVATED)
    @JsonProperty(value = "articleViolationTypeId")
    private ArticleViolationType articleViolationType;

    public ProtocolArticle buildProtocolArticle() {
        ProtocolArticle article = new ProtocolArticle();

        article.setArticle(this.articlePart.getArticle());
        article.setArticlePart(this.articlePart);
        article.setArticleViolationType(this.articleViolationType);

        return article;
    }

    public ArticlePairJson buildArticlePairJson() {
        return new ArticlePairJson(this.articlePart, this.articleViolationType);
    }

}
