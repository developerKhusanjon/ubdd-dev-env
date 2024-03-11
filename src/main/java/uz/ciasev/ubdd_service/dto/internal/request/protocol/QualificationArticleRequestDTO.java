package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.config.context.ApplicationContextProvider;
import uz.ciasev.ubdd_service.dto.internal.request.ArticleRequest;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.repository.dict.article.ArticlePartRepository;
import uz.ciasev.ubdd_service.utils.types.ArticlePairJson;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.ConsistArticle;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"articlePart", "articleViolationType"})
@ConsistArticle
public class QualificationArticleRequestDTO implements ArticleRequest {

    @NotNull(message = ErrorCode.ARTICLE_REQUIRED)
    @ActiveOnly(message = "ARTICLE_DEACTIVATED")
    @JsonProperty(value = "articleId")
    private Article article;

    @JsonProperty(value = "articlePartId")
    private ArticlePart articlePart;

    @ActiveOnly(message = ErrorCode.ARTICLE_VIOLATION_TYPE_DEACTIVATED)
    @JsonProperty(value = "articleViolationTypeId")
    private ArticleViolationType articleViolationType;

    public ProtocolArticle buildProtocolArticle() {
        ProtocolArticle article = new ProtocolArticle();

        article.setArticle(this.article);

        if (articlePart != null) {
            article.setArticlePart(this.articlePart);
            article.setArticleViolationType(this.articleViolationType);
        } else {
            ArticlePartRepository articlePartRepository = ApplicationContextProvider.getContext().getBean(ArticlePartRepository.class);
            List<ArticlePart> articleParts = articlePartRepository.findAllByArticleId(this.article.getId());
            if (!articleParts.isEmpty()) {
                article.setArticlePart(articleParts.get(0));
                article.setArticleViolationType(null);
            }
        }

        return article;
    }

    public ArticlePairJson buildArticlePairJson() {
        return new ArticlePairJson(this.articlePart, this.articleViolationType);
    }

}
