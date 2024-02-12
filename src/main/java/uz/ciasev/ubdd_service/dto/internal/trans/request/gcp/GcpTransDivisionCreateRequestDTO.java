package uz.ciasev.ubdd_service.dto.internal.trans.request.gcp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.GcpTransDivisionCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransDivision;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;
import uz.ciasev.ubdd_service.utils.validator.ValidMultiLanguage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class GcpTransDivisionCreateRequestDTO extends GcpTransEntityCreateRequestDTO
        implements GcpTransDivisionCreateDTOI, TransEntityCreateRequest<GcpTransDivision> {

    @NotNull(message = ErrorCode.COUNTRY_ID_REQUIRED)
    @JsonProperty(value = "countryId")
    private Country country;

    @JsonProperty(value = "regionId")
    private Region region;

    @JsonProperty(value = "districtId")
    private District district;

    @NotNull(message = ErrorCode.GCP_DIVISION_NAME_REQUIRED)
    @ValidMultiLanguage
    private MultiLanguage name;

    @NotNull(message = ErrorCode.GCP_DIVISION_ADDRESS_REQUIRED)
    @Size(min = 1, max = 3000, message = ErrorCode.GCP_DIVISION_ADDRESS_MIN_MAX_LENGTH)
    private String address;

    @Override
    public void applyToNew(GcpTransDivision entity) {
        entity.construct(this);
    }
}
