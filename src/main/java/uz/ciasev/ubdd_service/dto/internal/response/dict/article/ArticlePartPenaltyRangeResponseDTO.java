package uz.ciasev.ubdd_service.dto.internal.response.dict.article;

import lombok.Getter;

@Getter
public class ArticlePartPenaltyRangeResponseDTO {

    private final Long minAmountPenalty;
    private final Long maxAmountPenalty;


    public ArticlePartPenaltyRangeResponseDTO(Long minAmountPenalty, Long maxAmountPenalty) {
        this.minAmountPenalty = minAmountPenalty;
        this.maxAmountPenalty = maxAmountPenalty;
    }
}
