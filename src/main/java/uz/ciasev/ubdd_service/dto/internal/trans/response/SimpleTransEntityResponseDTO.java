package uz.ciasev.ubdd_service.dto.internal.trans.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.trans.AbstractSimpleTransEntity;

@Getter
public class SimpleTransEntityResponseDTO {
    private final Long id;
    private final Long internalId;
    private final Long externalId;

    public SimpleTransEntityResponseDTO(AbstractSimpleTransEntity<? extends AbstractDict> entity) {
        this.id = entity.getId();
        this.internalId = entity.getInternalId();
        this.externalId = entity.getExternalId();
    }
}
