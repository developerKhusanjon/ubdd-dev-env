package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalDictEntity;
import uz.ciasev.ubdd_service.entity.dict.requests.ExternalDictCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalDictCreateRequestDTO<T extends AbstractExternalDictEntity>
        extends ExternalDictUpdateRequestDTO<T>
        implements DictCreateRequest<T>, ExternalDictCreateDTOI {

    @NotNull(message = ErrorCode.ID_REQUIRED)
    private Long id;

    @Override
    public void applyToNew(T entity) {
        entity.construct(this);
    }
}
