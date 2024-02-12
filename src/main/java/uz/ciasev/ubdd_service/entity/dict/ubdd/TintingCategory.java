package uz.ciasev.ubdd_service.entity.dict.ubdd;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.entity.dict.requests.TintingCategoryDTOI;
import uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd.TintingCategoryDeserializer;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "d_tinting_category")
@NoArgsConstructor
@JsonDeserialize(using = TintingCategoryDeserializer.class)
public class TintingCategory extends AbstractEmiDict {

    @Getter
    private BigDecimal percentage;

    public void construct(TintingCategoryDTOI request) {
        super.construct(request);
        set(request);
    }

    public void update(TintingCategoryDTOI request) {
        super.update(request);
        set(request);
    }

    private void set(TintingCategoryDTOI request) {
        this.percentage = request.getPercentage();
    }
}
