package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.Mtp;
import uz.ciasev.ubdd_service.entity.dict.requests.DictUpdateDTOI;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

@EqualsAndHashCode(callSuper = true)
@Getter
public class MtpUpdateRequestDTO extends BaseDictRequestDTO implements DictUpdateDTOI, DictUpdateRequest<Mtp> {

    @Override
    public void applyToOld(Mtp entity) {
        entity.update(this);
    }
}
