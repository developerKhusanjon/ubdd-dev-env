package uz.ciasev.ubdd_service.dto.internal.trans.request.autocon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.SimpleTransEntityCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.autocon.AutoconTransRegion;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
public class AutoconTransRegionCreateRequestDTO implements SimpleTransEntityCreateDTOI<Region>, TransEntityCreateRequest<AutoconTransRegion> {

    @NotNull(message = ErrorCode.EXTERNAL_ID_REQUIRED)
    private Long externalId;

    @NotNull(message = ErrorCode.INTERNAL_ID_REQUIRED)
    @JsonProperty(value = "internalId")
    private Region internal;

    @Override
    public void applyToNew(AutoconTransRegion entity) {
        entity.construct(this);
    }
}
