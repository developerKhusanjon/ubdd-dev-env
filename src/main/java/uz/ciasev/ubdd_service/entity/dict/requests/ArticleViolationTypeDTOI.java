package uz.ciasev.ubdd_service.entity.dict.requests;

import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

public interface ArticleViolationTypeDTOI extends DictCreateDTOI, DictUpdateDTOI {
    Integer getNumber();
    Boolean getDontCheckUniqueness();
    MultiLanguage getShortName();
    String getTrafficRulesClause();
    String getRadarFabulaDescription();
}
