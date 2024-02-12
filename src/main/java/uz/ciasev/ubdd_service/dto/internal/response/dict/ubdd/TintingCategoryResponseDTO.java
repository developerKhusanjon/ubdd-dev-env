package uz.ciasev.ubdd_service.dto.internal.response.dict.ubdd;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.ubdd.TintingCategory;

import java.math.BigDecimal;

@Getter
public class TintingCategoryResponseDTO extends DictResponseDTO {

    private final BigDecimal percentage;

    public TintingCategoryResponseDTO(TintingCategory entity) {
        super(entity);
        this.percentage = entity.getPercentage();
    }
}
