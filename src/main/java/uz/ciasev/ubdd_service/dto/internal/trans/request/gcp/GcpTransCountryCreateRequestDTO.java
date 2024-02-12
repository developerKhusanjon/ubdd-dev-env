package uz.ciasev.ubdd_service.dto.internal.trans.request.gcp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.SimpleGcpTransEntityCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransCountry;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class GcpTransCountryCreateRequestDTO extends GcpTransEntityCreateRequestDTO
        implements SimpleGcpTransEntityCreateDTOI<Country>, TransEntityCreateRequest<GcpTransCountry> {

    @NotNull(message = ErrorCode.INTERNAL_ID_REQUIRED)
    @JsonProperty(value = "internalId")
    private Country internal;

    @Override
    public void applyToNew(GcpTransCountry entity) {
        entity.construct(this, internal.getName());
    }
}
