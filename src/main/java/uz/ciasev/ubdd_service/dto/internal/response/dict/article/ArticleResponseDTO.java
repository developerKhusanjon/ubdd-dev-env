package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Getter
public class ArticleResponseDTO extends DictResponseDTO {
    private final MultiLanguage shortName;
    private final Integer number;
    private final Integer prim;
    @Deprecated
    private final Boolean hasParts = true;
    private final Long articleHeadId;

    public ArticleResponseDTO(Article article) {
        super(article);
        this.number = article.getNumber();
        this.prim = article.getPrim();
        this.shortName = article.getShortName();
        this.articleHeadId = article.getArticleHeadId();
    }
}
