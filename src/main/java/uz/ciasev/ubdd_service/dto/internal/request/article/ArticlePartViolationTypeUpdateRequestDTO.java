package uz.ciasev.ubdd_service.dto.internal.request.article;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartViolationType;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Data
public class ArticlePartViolationTypeUpdateRequestDTO {

    @NotNull(message = ErrorCode.DISCOUNT_FLAG_REQUIRED)
    private Boolean isDiscount;

    public ArticlePartViolationType apply(ArticlePartViolationType articlePartViolationType) {
        articlePartViolationType.setDiscount(this.isDiscount);
        return articlePartViolationType;
    }

    public ArticlePartViolationType build() {
        return apply(new ArticlePartViolationType());
    }
}
