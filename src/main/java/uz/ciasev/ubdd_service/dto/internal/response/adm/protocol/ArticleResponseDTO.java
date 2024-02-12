package uz.ciasev.ubdd_service.dto.internal.response.adm.protocol;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartViolationType;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolArticle;
import uz.ciasev.ubdd_service.utils.types.ArticlePair;

import java.util.Optional;

@Getter
@Setter
public class ArticleResponseDTO {

    private final Long id;
    private final Boolean isMain;
    private final Long articleId;
    private final Long articlePartId;
    private final Long articleViolationTypeId;
    private Long articlePartViolationTypeId;

    public ArticleResponseDTO(ProtocolArticle article, Optional<ArticlePartViolationType> articlePartViolationType) {
        this(article);
        articlePartViolationType.ifPresent(a -> this.articlePartViolationTypeId = a.getId());

    }

    public ArticleResponseDTO(ProtocolArticle article) {
        this.id = article.getId();
        this.isMain = article.getIsMain();
        this.articleId = article.getArticleId();
        this.articlePartId = article.getArticlePartId();
        this.articleViolationTypeId = article.getArticleViolationTypeId();
    }

    public ArticleResponseDTO(ArticlePair article) {
        id = null;
        isMain = null;
        this.articleId = article.getArticleId();
        this.articlePartId = article.getArticlePartId();
        this.articleViolationTypeId = article.getArticleViolationTypeId();
    }
}
