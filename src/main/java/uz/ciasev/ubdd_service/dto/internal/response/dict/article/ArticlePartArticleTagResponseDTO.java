package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartArticleTag;

@Data
public class ArticlePartArticleTagResponseDTO {
    private final Long id;
    private final Long articlePartId;
    private final Long articleTagId;

    public ArticlePartArticleTagResponseDTO(ArticlePartArticleTag apat) {
        this.id = apat.getId();
        this.articlePartId = apat.getArticlePartId();
        this.articleTagId = apat.getArticleTagId();
    }
}
