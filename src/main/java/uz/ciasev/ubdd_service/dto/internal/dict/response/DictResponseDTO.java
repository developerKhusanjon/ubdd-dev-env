package uz.ciasev.ubdd_service.dto.internal.dict.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.DictEntity;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.time.LocalDate;

@Getter
public class DictResponseDTO {

    private final Long id;
    private final MultiLanguage name;
    private final Boolean isActive;
    private final String code;
    private final LocalDate openDate;
    private final LocalDate closeDate;

    public DictResponseDTO(DictEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.isActive = entity.getIsActive();
        this.code = entity.getCode();
        this.openDate = entity.getOpenedDate();
        this.closeDate = entity.getClosedDate();
    }
}
