package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleViolationTypeResponseDTO {

    private Long id;
    private MultiLanguage name;
    private String code;
    private Boolean isActive;

    public ArticleViolationTypeResponseDTO(ArticleViolationType articleViolationType) {
        this.id = articleViolationType.getId();
        this.name = articleViolationType.getName();
        this.code = articleViolationType.getCode();
        this.isActive = articleViolationType.getIsActive();
    }
}
