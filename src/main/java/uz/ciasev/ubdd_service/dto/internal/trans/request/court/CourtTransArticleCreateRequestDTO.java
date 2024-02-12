package uz.ciasev.ubdd_service.dto.internal.trans.request.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.CourtTransArticleCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransArticle;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
public class CourtTransArticleCreateRequestDTO implements CourtTransArticleCreateDTOI, TransEntityCreateRequest<CourtTransArticle> {

    @NotNull(message = ErrorCode.EXTERNAL_ARTICLE_ID_REQUIRED)
    private Long externalArticleId;

    private Long externalArticlePartId;

    @JsonProperty(value = "articlePartId")
    @NotNull(message = ErrorCode.ARTICLE_PART_REQUIRED)
    private ArticlePart articlePart;

    @Override
    public void applyToNew(CourtTransArticle entity) {
        entity.construct(this);
    }
}
