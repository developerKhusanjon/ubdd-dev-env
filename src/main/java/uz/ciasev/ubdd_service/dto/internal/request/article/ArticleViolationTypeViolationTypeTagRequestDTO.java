package uz.ciasev.ubdd_service.dto.internal.request.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import uz.ciasev.ubdd_service.entity.dict.article.*;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

public class ArticleViolationTypeViolationTypeTagRequestDTO {

    @NotNull(message = ErrorCode.VIOLATION_TYPE_REQUIRED)
    @JsonProperty(value = "violationTypeId")
    private ArticleViolationType violationType;

    @NotNull(message = ErrorCode.VIOLATION_TYPE_TAG_REQUIRED)
    @JsonProperty(value = "violationTypeTagId")
    private ArticleViolationTypeTag violationTypeTag;

    public ArticleViolationTypeViolationTypeTag apply(ArticleViolationTypeViolationTypeTag typeTag) {
        typeTag.setArticleViolationType(this.violationType);
        typeTag.setArticleViolationTypeTag(this.violationTypeTag);
        return typeTag;
    }

    public ArticleViolationTypeViolationTypeTag build() {
        return apply(new ArticleViolationTypeViolationTypeTag());
    }

    public Long getArticleViolationTypeId(){
        if (violationType == null) {
            return null;
        }
        return violationType.getId();
    }

    public Long getArticleViolationTypeTagId(){
        if (violationTypeTag == null) {
            return null;
        }
        return violationTypeTag.getId();
    }
}
