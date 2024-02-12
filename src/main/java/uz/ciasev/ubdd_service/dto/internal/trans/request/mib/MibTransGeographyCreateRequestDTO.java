package uz.ciasev.ubdd_service.dto.internal.trans.request.mib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.MibTransGeographyCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.mib.MibTransGeography;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
public class MibTransGeographyCreateRequestDTO implements MibTransGeographyCreateDTOI, TransEntityCreateRequest<MibTransGeography> {

    @NotNull(message = ErrorCode.EXTERNAL_ID_REQUIRED)
    private Long externalId;

    @JsonProperty(value = "regionId")
    @NotNull(message = ErrorCode.REGION_REQUIRED)
    private Region region;

    @JsonProperty(value = "districtId")
    private District district;

    private Boolean isAvailableForSendMibExecutionCard;

    private Boolean isAvailableForMibProtocolRegistration;

    @Override
    public void applyToNew(MibTransGeography entity) {
        entity.construct(this);
    }
}
