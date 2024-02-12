package uz.ciasev.ubdd_service.dto.internal.response.dict.person;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.person.AgeCategory;

@Getter
public class AgeCategoryResponseDTO extends DictResponseDTO {
    private final Integer ageFrom;
    private final Integer ageTo;
    private final Boolean isJuvenile;
    private final Boolean isViolatorOnly;

    public AgeCategoryResponseDTO(AgeCategory ageCategory) {
        super(ageCategory);
        this.ageFrom = ageCategory.getAgeFrom();
        this.ageTo = ageCategory.getAgeTo();
        this.isJuvenile = ageCategory.isJuvenile();
        this.isViolatorOnly = ageCategory.isViolatorOnly();
    }
}
