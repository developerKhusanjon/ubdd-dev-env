package uz.ciasev.ubdd_service.mvd_core.citizenapi.article;

import lombok.Getter;
import uz.ciasev.ubdd_service.mvd_core.citizenapi.MultiLanguageResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartDetail;
import uz.ciasev.ubdd_service.entity.dict.article.PenaltyRangeForDateDTO;

@Getter
public class ArticlePartDetailResponseDTO extends ArticlePartResponseDTO {

    private final MultiLanguageResponseDTO violation;

    private final MultiLanguageResponseDTO punishment;
    private final Long personMinPenalty;
    private final Long personMaxPenalty;
    private final Long juridicMinPenalty;
    private final Long juridicMaxPenalty;

    public ArticlePartDetailResponseDTO(ArticlePart articlePart, ArticlePartDetail detail, PenaltyRangeForDateDTO rangeDTO) {
        super(articlePart);
        this.violation = new MultiLanguageResponseDTO(detail.getViolationText());
        this.punishment = new MultiLanguageResponseDTO(detail.getPunishmentText());
        this.personMinPenalty = rangeDTO.getPersonMin().orElse(null);
        this.personMaxPenalty = rangeDTO.getPersonMax().orElse(null);
        this.juridicMinPenalty = rangeDTO.getJuridicMin().orElse(null);
        this.juridicMaxPenalty = rangeDTO.getJuridicMax().orElse(null);
    }
}
