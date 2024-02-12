package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.requests.UBDDImpoundCreateDTOI;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDImpound;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class UBDDImpoundCreateRequestDTO extends UBDDImpoundUpdateRequestDTO implements UBDDImpoundCreateDTOI, DictCreateRequest<UBDDImpound> {

    @NotNull(message = ErrorCode.ID_REQUIRED)
    private Long id;

    @Override
    public void applyToNew(UBDDImpound entity) {
        entity.construct(this);
    }
}
