package uz.ciasev.ubdd_service.dto.internal.dict.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendStatusDict;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalStatusDictEntity;
import uz.ciasev.ubdd_service.entity.dict.AliasedDictEntity;

@Getter
public class AliasedStatusDictResponseDTO extends AliasedDictResponseDTO {

    private final String color;

    public <T extends AbstractBackendStatusDict<?>> AliasedStatusDictResponseDTO(T entity) {
        super(entity);
        this.color = entity.getColor();
    }

    public <T extends AbstractExternalStatusDictEntity & AliasedDictEntity<?>> AliasedStatusDictResponseDTO(T entity) {
        super(entity);
        this.color = entity.getColor();
    }
}
