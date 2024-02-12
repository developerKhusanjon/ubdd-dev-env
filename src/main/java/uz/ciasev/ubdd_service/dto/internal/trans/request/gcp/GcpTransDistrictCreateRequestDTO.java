package uz.ciasev.ubdd_service.dto.internal.trans.request.gcp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.SimpleGcpTransEntityCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransDistrict;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class GcpTransDistrictCreateRequestDTO extends GcpTransEntityCreateRequestDTO
        implements SimpleGcpTransEntityCreateDTOI<District>, TransEntityCreateRequest<GcpTransDistrict> {

    @NotNull(message = ErrorCode.INTERNAL_ID_REQUIRED)
    @JsonProperty(value = "internalId")
    private District internal;

    @Override
    public void applyToNew(GcpTransDistrict entity) {
        entity.construct(this, internal.getName());
    }
}
