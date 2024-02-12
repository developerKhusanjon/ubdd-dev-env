package uz.ciasev.ubdd_service.dto.internal.dict.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleHead;
import uz.ciasev.ubdd_service.entity.dict.requests.ArticleCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ArticleCreateRequestDTO extends ArticleUpdateRequestDTO implements ArticleCreateDTOI, DictCreateRequest<Article> {
    @NotNull(message = ErrorCode.ARTICLE_HEAD_ID_REQUIRED)
    @JsonProperty(value = "articleHeadId")
    private ArticleHead articleHead;

    @Override
    public void applyToNew(Article entity) {
        entity.construct(this);
    }
}
