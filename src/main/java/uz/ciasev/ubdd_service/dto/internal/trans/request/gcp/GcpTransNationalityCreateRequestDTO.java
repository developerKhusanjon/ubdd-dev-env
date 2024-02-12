package uz.ciasev.ubdd_service.dto.internal.trans.request.gcp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.person.Nationality;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.SimpleGcpTransEntityCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransNationality;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class GcpTransNationalityCreateRequestDTO extends GcpTransEntityCreateRequestDTO
        implements SimpleGcpTransEntityCreateDTOI<Nationality>, TransEntityCreateRequest<GcpTransNationality> {

    @NotNull(message = ErrorCode.INTERNAL_ID_REQUIRED)
    @JsonProperty(value = "internalId")
    private Nationality internal;

    @Override
    public void applyToNew(GcpTransNationality entity) {
        entity.construct(this, internal.getName());
    }
}
