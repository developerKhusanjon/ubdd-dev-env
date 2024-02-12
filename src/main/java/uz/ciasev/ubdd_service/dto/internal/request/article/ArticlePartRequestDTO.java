package uz.ciasev.ubdd_service.dto.internal.request.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartDetail;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartHardLevel;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleParticipantType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;
import uz.ciasev.ubdd_service.utils.validator.ValidArticlePart;
import uz.ciasev.ubdd_service.utils.validator.ValidMultiLanguage;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@ValidArticlePart
public class ArticlePartRequestDTO {

    @NotNull(message = ErrorCode.ARTICLE_PART_IS_VICTIM_REQUIRED)
    private Boolean isVictim;

    @NotNull(message = ErrorCode.ARTICLE_PART_IS_DISCOUNT_REQUIRED)
    private Boolean isDiscount;

    @NotNull(message = ErrorCode.ARTICLE_PART_IS_PENALTY_ONLY_REQUIRED)
    private Boolean isPenaltyOnly;

    @NotNull(message = ErrorCode.ARTICLE_PART_IS_COURT_ONLY_REQUIRED)
    private Boolean isCourtOnly;

    private List<ArticlePartPenaltyRangeDTO> penaltyRange;

    @Valid
    @ValidMultiLanguage
    @NotNull(message = ErrorCode.ARTICLE_PART_VIOLATION_TEXT_REQUIRED)
    private MultiLanguage violationText;

    @Valid
    @ValidMultiLanguage
    @NotNull(message = ErrorCode.ARTICLE_PART_PUNISHMENT_TEXT_REQUIRED)
    private MultiLanguage punishmentText;

    @NotNull(message = ErrorCode.ARTICLE_PART_PARTICIPANT_TYPE_REQUIRED)
    @JsonProperty(value = "articleParticipantTypeId")
    private ArticleParticipantType articleParticipantType;

//    @NotNull(message = ErrorCode.ARTICLE_PART_HARD_LEVEL_REQUIRED)
    @JsonProperty(value = "articlePartHardLevelId")
    private ArticlePartHardLevel articlePartHardLevel;

    @DecimalMax(value = "99.9999", message = ErrorCode.PENALTY_POINT_MAX_LENGTH)
    private Double penaltyPoint;

    @NotNull(message = ErrorCode.IS_VIOLATION_TYPE_REQUIRED)
    private Boolean isViolationTypeRequired;

    @NotNull(message = ErrorCode.NUMBER_REQUIRED)
    @Max(value = 99, message = ErrorCode.NUMBER_MIN_MAX_LENGTH)
    @Min(value = 0, message = ErrorCode.NUMBER_MIN_MAX_LENGTH)
    private Integer number;


    public void applyTo(ArticlePart articlePart) {
        articlePart.setArticleParticipantType(this.articleParticipantType);
        articlePart.setVictim(this.isVictim);
        articlePart.setDiscount(this.isDiscount);
        articlePart.setPenaltyOnly(this.isPenaltyOnly);
        articlePart.setCourtOnly(this.isCourtOnly);
        articlePart.setHardLevel(this.articlePartHardLevel);
        articlePart.setPenaltyPoint(this.penaltyPoint);
        articlePart.setIsViolationTypeRequired(this.isViolationTypeRequired);
        articlePart.setNumber(this.number);
    }

    public void applyTo(ArticlePartDetail articlePartDetail) {
        articlePartDetail.setPunishmentText(this.getPunishmentText());
        articlePartDetail.setViolationText(this.getViolationText());
    }

}
