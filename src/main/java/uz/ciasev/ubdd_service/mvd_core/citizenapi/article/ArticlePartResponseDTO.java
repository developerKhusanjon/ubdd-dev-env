package uz.ciasev.ubdd_service.mvd_core.citizenapi.article;

import lombok.Getter;
import uz.ciasev.ubdd_service.mvd_core.citizenapi.MultiLanguageResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;

@Getter
public class ArticlePartResponseDTO {

    private final Long id;

    private final Long articleId;

    private final String code;

    private final Integer number;

    private final MultiLanguageResponseDTO name;

    private final MultiLanguageResponseDTO shortName;

    public ArticlePartResponseDTO(ArticlePart articlePart) {
        this.id = articlePart.getId();
        this.articleId = articlePart.getArticleId();
        this.code = articlePart.getCode();
        this.number = articlePart.getNumber();
        this.name = new MultiLanguageResponseDTO(articlePart.getName());
        this.shortName = new MultiLanguageResponseDTO(articlePart.getShortName());
    }
}
