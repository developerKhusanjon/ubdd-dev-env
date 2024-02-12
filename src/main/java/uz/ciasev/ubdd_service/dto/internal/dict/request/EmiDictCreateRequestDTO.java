package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.AbstractEmiDict;
import uz.ciasev.ubdd_service.entity.dict.requests.DictCreateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmiDictCreateRequestDTO<T extends AbstractEmiDict> extends EmiDictUpdateRequestDTO<T> implements DictCreateRequest<T>, DictCreateDTOI {

    @Override
    public void applyToNew(T entity) {
        entity.construct(this);
    }
}
