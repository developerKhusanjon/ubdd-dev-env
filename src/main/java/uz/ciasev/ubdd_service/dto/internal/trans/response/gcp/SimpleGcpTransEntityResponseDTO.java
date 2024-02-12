package uz.ciasev.ubdd_service.dto.internal.trans.response.gcp;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.AbstractDict;
import uz.ciasev.ubdd_service.entity.trans.gcp.AbstractSimpleGcpTransEntity;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Getter
public class SimpleGcpTransEntityResponseDTO extends AbstractGcpTransEntityResponseDTO {

    private final Long internalId;
    private final MultiLanguage name;

    public SimpleGcpTransEntityResponseDTO(AbstractSimpleGcpTransEntity<? extends AbstractDict> entity) {
        super(entity);
        this.internalId = entity.getInternalId();
        this.name = entity.getName();
    }
}
