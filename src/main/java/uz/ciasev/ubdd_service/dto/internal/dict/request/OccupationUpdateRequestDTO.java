package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;
import uz.ciasev.ubdd_service.entity.dict.requests.OccupationUpdateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class OccupationUpdateRequestDTO extends ExternalDictUpdateRequestDTO<Occupation> implements OccupationUpdateDTOI {

    @NotNull(message = ErrorCode.IS_WORKER_REQUIRED)
    Boolean isWorker;

    @Override
    public void applyToOld(Occupation entity) {
        entity.update(this);
    }
}
