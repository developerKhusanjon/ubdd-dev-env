package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;
import uz.ciasev.ubdd_service.entity.dict.requests.OccupationCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class OccupationCreateRequestDTO extends OccupationUpdateRequestDTO implements OccupationCreateDTOI, DictCreateRequest<Occupation> {

    @NotNull(message = ErrorCode.ID_REQUIRED)
    private Long id;

    @Override
    public void applyToNew(Occupation entity) {
        entity.construct(this);
    }
}
