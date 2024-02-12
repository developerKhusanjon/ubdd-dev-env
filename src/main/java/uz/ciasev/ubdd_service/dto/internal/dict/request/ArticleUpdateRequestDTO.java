package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.requests.ArticleUpdateDTOI;
import uz.ciasev.ubdd_service.entity.dict.article.Article;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;
import uz.ciasev.ubdd_service.utils.validator.ValidMultiLanguage;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
public class ArticleUpdateRequestDTO implements ArticleUpdateDTOI, DictUpdateRequest<Article> {

    @NotNull(message = ErrorCode.NAME_REQUIRED)
    @ValidMultiLanguage
    private MultiLanguage name;

    @NotNull(message = ErrorCode.NUMBER_REQUIRED)
    @Max(value = 999, message = ErrorCode.NUMBER_MIN_MAX_LENGTH)
    @Min(value = 1, message = ErrorCode.NUMBER_MIN_MAX_LENGTH)
    private Integer number;

//    @NotNull(message = ErrorCode.PRIM_REQUIRED)
    @Max(value = 99, message = ErrorCode.PRIM_MIN_MAX_LENGTH)
    @Min(value = 0, message = ErrorCode.PRIM_MIN_MAX_LENGTH)
    private Integer prim;

    @Override
    public void applyToOld(Article entity) {
        entity.update(this);
    }

    public Integer getPrim() {
        return prim == null ? 0 : prim;
    }
}
