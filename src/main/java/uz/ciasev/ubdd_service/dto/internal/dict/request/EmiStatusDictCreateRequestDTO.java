package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiStatusDict;
import uz.ciasev.ubdd_service.entity.dict.requests.StatusDictCreateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmiStatusDictCreateRequestDTO<T extends AbstractEmiStatusDict> extends EmiStatusDictUpdateRequestDTO<T> implements DictCreateRequest<T>, StatusDictCreateDTOI {

    @Override
    public void applyToNew(T entity) {
        entity.construct(this);
    }
}
