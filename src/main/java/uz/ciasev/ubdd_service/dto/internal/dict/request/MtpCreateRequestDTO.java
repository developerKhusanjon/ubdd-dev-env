package uz.ciasev.ubdd_service.dto.internal.dict.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Mtp;
import uz.ciasev.ubdd_service.entity.dict.requests.MtpCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
public class MtpCreateRequestDTO extends MtpUpdateRequestDTO implements MtpCreateDTOI, DictCreateRequest<Mtp> {

    @NotNull(message = ErrorCode.DISTRICT_REQUIRED)
    @JsonProperty(value = "districtId")
    private District district;

    @Override
    public void applyToNew(Mtp entity) {
        entity.construct(this);
    }
}
