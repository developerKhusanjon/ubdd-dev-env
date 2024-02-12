package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDImpoundUpdateDTOI;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDImpound;

@EqualsAndHashCode(callSuper = true)
@Data
public class UBDDImpoundUpdateRequestDTO extends ExternalDictUpdateRequestDTO<UBDDImpound> implements UBDDImpoundUpdateDTOI {

    private Long vehicleArrestPlaceId;

    @Override
    public void applyToOld(UBDDImpound entity) {
        entity.update(this);
    }
}
