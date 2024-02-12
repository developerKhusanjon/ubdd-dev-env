package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringAddition;
import uz.ciasev.ubdd_service.entity.dict.requests.ExternalDictUpdateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

@EqualsAndHashCode(callSuper = true)
@Getter
public class CourtConsideringAdditionUpdateRequestDTO extends BaseDictRequestDTO implements ExternalDictUpdateDTOI, DictUpdateRequest<CourtConsideringAddition> {

    @Override
    public void applyToOld(CourtConsideringAddition entity) {
        entity.update(this);
    }
}
