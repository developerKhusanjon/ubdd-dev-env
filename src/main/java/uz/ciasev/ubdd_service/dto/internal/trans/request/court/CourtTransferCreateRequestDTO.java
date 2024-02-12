package uz.ciasev.ubdd_service.dto.internal.trans.request.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.CourtTransferCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransfer;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
public class CourtTransferCreateRequestDTO implements CourtTransferCreateDTOI, TransEntityCreateRequest<CourtTransfer> {

    @NotNull(message = ErrorCode.EXTERNAL_ID_REQUIRED)
    private Long externalId;

    @JsonProperty(value = "regionId")
    @NotNull(message = ErrorCode.REGION_REQUIRED)
    private Region region;

    @JsonProperty(value = "districtId")
    private District district;

    @Override
    public void applyToNew(CourtTransfer entity) {
        entity.construct(this);
    }
}
