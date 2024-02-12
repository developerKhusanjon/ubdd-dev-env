package uz.ciasev.ubdd_service.mvd_core.citizenapi;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.DictEntity;

@Getter
public class DictResponseDTO {

    private final Long id;

    private final MultiLanguageResponseDTO name;

    private final Boolean isActive;

    public DictResponseDTO(DictEntity dist) {
        this.id = dist.getId();
        this.name = new MultiLanguageResponseDTO(dist.getName());
        this.isActive = dist.getIsActive();
    }

    public static DictResponseDTO ofNullable(DictEntity aliasedDict) {
        if (aliasedDict == null) {
            return null;
        }

        return new DictResponseDTO(aliasedDict);
    }

}
