package uz.ciasev.ubdd_service.mvd_core.citizenapi.article;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.article.ArticleViolationType;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Getter
public class ArticleViolationTypeResponseDTO extends DictResponseDTO {

    private final MultiLanguage shortName;
    private final String trafficRulesClause;
    private final Integer number;
    private final Boolean dontCheckUniqueness;
    private final String radarFabulaDescription;

    public ArticleViolationTypeResponseDTO(ArticleViolationType entity) {
        super(entity);
        this.shortName = entity.getShortName();
        this.trafficRulesClause = entity.getTrafficRulesClause();
        this.number = entity.getNumber();
        this.dontCheckUniqueness = entity.getDontCheckUniqueness();
        this.radarFabulaDescription = entity.getRadarFabulaDescription();
    }
}
