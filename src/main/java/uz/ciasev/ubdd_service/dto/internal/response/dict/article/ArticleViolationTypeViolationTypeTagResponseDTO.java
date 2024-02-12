package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationTypeViolationTypeTag;

@Data
public class ArticleViolationTypeViolationTypeTagResponseDTO {
    private final Long id;
    private final Long articleViolationTypeId;
    private final Long articleViolationTypeTagId;

    public ArticleViolationTypeViolationTypeTagResponseDTO(ArticleViolationTypeViolationTypeTag typeTag) {
        this.id = typeTag.getId();
        this.articleViolationTypeId = typeTag.getArticleViolationTypeId();
        this.articleViolationTypeTagId = typeTag.getArticleViolationTypeTagId();
    }
}
