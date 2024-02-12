package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Getter
@AllArgsConstructor
public class ArticlePartResponseDTO {

    private final Long id;
    private final Long headId;
    private final Long articleId;
    private final Long article;
    private final String code;
    private final int number;
    private final MultiLanguage name;
    private final MultiLanguage shortName;
    private final Boolean isActive;
    private final Long articleParticipantTypeId;

    public ArticlePartResponseDTO(ArticlePart articlePart) {
        this.id = articlePart.getId();
        this.headId = articlePart.getArticle().getArticleHeadId();
        this.code = articlePart.getCode();
        this.number = articlePart.getNumber();
        this.name = articlePart.getName();
        this.shortName = articlePart.getShortName();
        this.article = articlePart.getArticleId();
        this.articleId = articlePart.getArticleId();
        this.articleParticipantTypeId = articlePart.getArticleParticipantTypeId();
        this.isActive = articlePart.getIsActive();
    }
}
