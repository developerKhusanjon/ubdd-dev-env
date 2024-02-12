package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendStatusDict;
import uz.ciasev.ubdd_service.entity.dict.requests.StatusDictUpdateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class BackendStatusDictUpdateRequestDTO<T extends AbstractBackendStatusDict<?>> extends BaseStatusDictRequestDTO implements DictUpdateRequest<T>, StatusDictUpdateDTOI {

    @Override
    public void applyToOld(T entity) {
        entity.update(this);
    }
}
