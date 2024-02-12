package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.AbstractBackendDict;
import uz.ciasev.ubdd_service.entity.dict.requests.DictUpdateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class BackendDictUpdateRequestDTO<T extends AbstractBackendDict<?>> extends BaseDictRequestDTO implements DictUpdateRequest<T>, DictUpdateDTOI {
    @Override
    public void applyToOld(T entity) {
        entity.update(this);
    }
}
