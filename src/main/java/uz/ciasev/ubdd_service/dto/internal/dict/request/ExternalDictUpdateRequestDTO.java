package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalDictEntity;
import uz.ciasev.ubdd_service.entity.dict.requests.ExternalDictUpdateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalDictUpdateRequestDTO<T extends AbstractExternalDictEntity>
        extends BaseDictRequestDTO
        implements DictUpdateRequest<T>, ExternalDictUpdateDTOI {
    @Override
    public void applyToOld(T entity) {
        entity.update(this);
    }
}
