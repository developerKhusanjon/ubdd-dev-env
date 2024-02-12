package uz.ciasev.ubdd_service.dto.internal.dict.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.dict.AliasedDictEntity;

@Getter
public class AliasedDictResponseDTO extends DictResponseDTO {

    private final String alias;

    public <T extends AbstractDict & AliasedDictEntity<?>> AliasedDictResponseDTO(T entity) {
        super(entity);
        this.alias = entity.getAlias().name();
    }
}
