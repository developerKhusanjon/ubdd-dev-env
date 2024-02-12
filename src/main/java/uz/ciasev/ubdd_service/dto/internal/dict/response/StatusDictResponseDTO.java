package uz.ciasev.ubdd_service.dto.internal.dict.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiStatusDict;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalStatusDictEntity;

@Getter
public class StatusDictResponseDTO extends DictResponseDTO {
    private final String color;

    public <T extends AbstractEmiStatusDict> StatusDictResponseDTO(T entity) {
        super(entity);
        this.color = entity.getColor();
    }

    public <T extends AbstractExternalStatusDictEntity> StatusDictResponseDTO(T entity) {
        super(entity);
        this.color = entity.getColor();
    }
}
