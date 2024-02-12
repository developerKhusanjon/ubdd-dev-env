package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartViolationType;
import uz.ciasev.ubdd_service.entity.settings.OrganArticleSettingsProjection;
import uz.ciasev.ubdd_service.utils.ConvertUtils;

@Data
public class ArticlePartViolationTypeResponseDTO {

    private String id;
    private Long articlePartId;
    private Long violationTypeId;
    private Boolean isDiscount;

    public ArticlePartViolationTypeResponseDTO(ArticlePartViolationType type) {
        this.id = ConvertUtils.getUniqueId("T", type.getId());
        this.articlePartId = type.getArticlePartId();
        this.violationTypeId = type.getArticleViolationTypeId();
        this.isDiscount = type.isDiscount();
    }

    public ArticlePartViolationTypeResponseDTO(OrganArticleSettingsProjection projection) {
        this.id = ConvertUtils.getUniqueId("A", projection.getId());
        this.articlePartId = projection.getArticlePartId();
        this.violationTypeId = null;
        this.isDiscount = projection.getIsDiscount();
    }
}
