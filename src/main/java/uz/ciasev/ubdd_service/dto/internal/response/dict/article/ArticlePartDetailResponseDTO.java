package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.request.article.ArticlePartPenaltyRangeDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartDetail;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartPenaltyRange;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticlePartDetailResponseDTO extends ArticlePartResponseDTO {

    private final Boolean isVictim;

    private final Boolean isDiscount;

    private final Boolean isPenaltyOnly;

    private final Boolean isCourtOnly;

    private final List<ArticlePartPenaltyRangeDTO> penaltyRange;

    private final MultiLanguage violationText;

    private final MultiLanguage punishmentText;

    private final Long articlePartHardLevelId;

    private final Double penaltyPoint;

    private final Boolean isViolationTypeRequired;

    public ArticlePartDetailResponseDTO(ArticlePart articlePart, ArticlePartDetail detail, List<ArticlePartPenaltyRange> penaltyRange) {

        super(articlePart);
        this.isVictim = articlePart.isVictim();
        this.isDiscount = articlePart.isDiscount();
        this.isPenaltyOnly = articlePart.isPenaltyOnly();
        this.isCourtOnly = articlePart.isCourtOnly();
        this.penaltyRange = penaltyRange.stream().map(ArticlePartPenaltyRangeDTO::of).collect(Collectors.toList());
        this.violationText = detail.getViolationText();
        this.punishmentText = detail.getPunishmentText();
        this.articlePartHardLevelId = articlePart.getHardLevelId();
        this.penaltyPoint = articlePart.getPenaltyPoint();
        this.isViolationTypeRequired = articlePart.getIsViolationTypeRequired();

    }
}
