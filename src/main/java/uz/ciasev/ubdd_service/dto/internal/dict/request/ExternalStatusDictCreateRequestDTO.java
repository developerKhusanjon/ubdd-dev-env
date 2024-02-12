package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.AbstractExternalStatusDictEntity;
import uz.ciasev.ubdd_service.entity.dict.requests.ExternalStatusDictCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExternalStatusDictCreateRequestDTO<T extends AbstractExternalStatusDictEntity>
        extends ExternalStatusDictUpdateRequestDTO<T>
        implements DictCreateRequest<T>, ExternalStatusDictCreateDTOI {

    @NotNull(message = ErrorCode.ID_REQUIRED)
    private Long id;

    @Override
    public void applyToNew(T entity) {
        entity.construct(this);
    }
}
