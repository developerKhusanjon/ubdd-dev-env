package uz.ciasev.ubdd_service.dto.internal.trans.request.gcp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.SimpleGcpTransEntityCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransRegion;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class GcpTransRegionCreateRequestDTO extends GcpTransEntityCreateRequestDTO
        implements SimpleGcpTransEntityCreateDTOI<Region>, TransEntityCreateRequest<GcpTransRegion> {

    @NotNull(message = ErrorCode.INTERNAL_ID_REQUIRED)
    @JsonProperty(value = "internalId")
    private Region internal;

    @Override
    public void applyToNew(GcpTransRegion entity) {
        entity.construct(this, internal.getName());
    }
}
