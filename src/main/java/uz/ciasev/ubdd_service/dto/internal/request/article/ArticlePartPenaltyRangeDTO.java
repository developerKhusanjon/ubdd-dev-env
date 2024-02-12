package uz.ciasev.ubdd_service.dto.internal.request.article;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePart;
import uz.ciasev.ubdd_service.entity.dict.article.ArticlePartPenaltyRange;

import java.time.LocalDate;

@Data
public class ArticlePartPenaltyRangeDTO {

    private LocalDate fromDate;
    private Integer personMinNumerator;
    private Integer personMinDenominator;
    private Integer personMaxNumerator;
    private Integer personMaxDenominator;
    private Integer juridicMinNumerator;
    private Integer juridicMinDenominator;
    private Integer juridicMaxNumerator;
    private Integer juridicMaxDenominator;

    public static ArticlePartPenaltyRangeDTO of(ArticlePartPenaltyRange penaltyRange) {

        ArticlePartPenaltyRangeDTO rsl = new ArticlePartPenaltyRangeDTO();

        rsl.setFromDate(penaltyRange.getFromDate());
        rsl.setPersonMinNumerator(penaltyRange.getPersonMinNumerator());
        rsl.setPersonMinDenominator(penaltyRange.getPersonMinDenominator());
        rsl.setPersonMaxNumerator(penaltyRange.getPersonMaxNumerator());
        rsl.setPersonMaxDenominator(penaltyRange.getPersonMaxDenominator());
        rsl.setJuridicMinNumerator(penaltyRange.getJuridicMinNumerator());
        rsl.setJuridicMinDenominator(penaltyRange.getJuridicMinDenominator());
        rsl.setJuridicMaxNumerator(penaltyRange.getJuridicMaxNumerator());
        rsl.setJuridicMaxDenominator(penaltyRange.getJuridicMaxDenominator());

        return rsl;
    }

    public ArticlePartPenaltyRange build(ArticlePart articlePart) {

        ArticlePartPenaltyRange rsl = new ArticlePartPenaltyRange();

        rsl.setArticlePart(articlePart);
        rsl.setFromDate(this.fromDate);
        rsl.setPersonMinNumerator(this.personMinNumerator);
        rsl.setPersonMinDenominator(this.personMinDenominator);
        rsl.setPersonMaxNumerator(this.personMaxNumerator);
        rsl.setPersonMaxDenominator(this.personMaxDenominator);
        rsl.setJuridicMinNumerator(this.juridicMinNumerator);
        rsl.setJuridicMinDenominator(this.juridicMinDenominator);
        rsl.setJuridicMaxNumerator(this.juridicMaxNumerator);
        rsl.setJuridicMaxDenominator(this.juridicMaxDenominator);

        return rsl;
    }
}
