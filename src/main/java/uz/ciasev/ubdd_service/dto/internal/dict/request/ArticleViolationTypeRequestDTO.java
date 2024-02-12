package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.entity.dict.requests.ArticleViolationTypeDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;
import uz.ciasev.ubdd_service.utils.validator.ValidMultiLanguage;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ArticleViolationTypeRequestDTO extends BaseDictRequestDTO implements ArticleViolationTypeDTOI, DictCreateRequest<ArticleViolationType>, DictUpdateRequest<ArticleViolationType> {
    @NotNull(message = ErrorCode.NUMBER_REQUIRED)
    @Max(value = 999, message = ErrorCode.NUMBER_MIN_MAX_LENGTH)
    @Min(value = 1, message = ErrorCode.NUMBER_MIN_MAX_LENGTH)
    private Integer number;

    @NotNull(message = ErrorCode.DONT_CHECK_UNIQUENESS_REQUIRED)
    private Boolean dontCheckUniqueness;

    @NotNull(message = ErrorCode.SHORT_NAME_REQUIRED)
    @ValidMultiLanguage
    private MultiLanguage shortName;

    @Size(min = 1, max = 16, message = ErrorCode.TRAFFIC_RULES_CLAUSE_MIN_MAX_SIZE)
    private String trafficRulesClause;

    @Size(min = 1, max = 1000, message = ErrorCode.RADAR_FABULA_DESCRIPTION_MIN_MAX_SIZE)
    private String radarFabulaDescription;

    @Override
    public void applyToNew(ArticleViolationType entity) {
        entity.construct(this);
    }

    @Override
    public void applyToOld(ArticleViolationType entity) {
        entity.update(this);
    }
}
