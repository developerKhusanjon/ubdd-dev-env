package uz.ciasev.ubdd_service.dto.internal.trans.request.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.MailTransGeographyCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.mail.MailTransGeography;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
public class MailTransGeographyCreateRequestDTO implements MailTransGeographyCreateDTOI, TransEntityCreateRequest<MailTransGeography> {

    @NotNull(message = ErrorCode.EXTERNAL_REGION_ID_REQUIRED)
    private Long externalRegionId;

    @NotNull(message = ErrorCode.EXTERNAL_DISTRICT_ID_REQUIRED)
    private Long externalDistrictId;

    @JsonProperty(value = "regionId")
    @NotNull(message = ErrorCode.REGION_REQUIRED)
    private Region region;

    @JsonProperty(value = "districtId")
    @NotNull(message = ErrorCode.DISTRICT_REQUIRED)
    private District district;

    @Override
    public void applyToNew(MailTransGeography entity) {
        entity.construct(this);
    }
}
