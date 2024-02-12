package uz.ciasev.ubdd_service.mvd_core.citizenapi.article;

import lombok.Getter;
import uz.ciasev.ubdd_service.mvd_core.citizenapi.MultiLanguageResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.Article;

@Getter
public class ArticleResponseDTO {

    private final Long id;

    private final Boolean isActive;

    private final Integer number;

    private final Integer prim;

    private final MultiLanguageResponseDTO name;

    private final MultiLanguageResponseDTO shortName;

    public ArticleResponseDTO(Article articlePart) {
        this.id = articlePart.getId();
        this.isActive = articlePart.getIsActive();
        this.number = articlePart.getNumber();
        this.prim = articlePart.getPrim();
        this.name = new MultiLanguageResponseDTO(articlePart.getName());
        this.shortName = new MultiLanguageResponseDTO(articlePart.getShortName());
    }
}
