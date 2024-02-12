package uz.ciasev.ubdd_service.dto.internal.request.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.article.*;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArticlePartCreateRequestDTO extends ArticlePartRequestDTO {

    @NotNull(message = ErrorCode.ARTICLE_REQUIRED)
    @JsonProperty(value = "articleId")
    private Article article;

    public void applyTo(ArticlePart articlePart) {
        super.applyTo(articlePart);
        articlePart.setArticle(this.article);
    }
}
