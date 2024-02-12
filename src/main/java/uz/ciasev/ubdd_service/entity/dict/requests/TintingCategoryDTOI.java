package uz.ciasev.ubdd_service.entity.dict.requests;

import java.math.BigDecimal;

public interface TintingCategoryDTOI extends DictCreateDTOI, DictUpdateDTOI {
    BigDecimal getPercentage();
}
