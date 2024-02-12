package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalStatusDictEntity;
import uz.ciasev.ubdd_service.entity.dict.requests.ExternalStatusDictUpdateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalStatusDictUpdateRequestDTO<T extends AbstractExternalStatusDictEntity>
        extends BaseStatusDictRequestDTO
        implements DictUpdateRequest<T>, ExternalStatusDictUpdateDTOI {

    @Override
    public void applyToOld(T entity) {
        entity.update(this);
    }
}
