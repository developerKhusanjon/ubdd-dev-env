package uz.ciasev.ubdd_service.dto.internal.trans.request.gcp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.SimpleGcpTransEntityCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.gcp.GcpTransGender;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class GcpTransGenderCreateRequestDTO extends GcpTransEntityCreateRequestDTO
        implements SimpleGcpTransEntityCreateDTOI<Gender>, TransEntityCreateRequest<GcpTransGender> {

    @NotNull(message = ErrorCode.INTERNAL_ID_REQUIRED)
    @JsonProperty(value = "internalId")
    private Gender internal;

    @Override
    public void applyToNew(GcpTransGender entity) {
        entity.construct(this, internal.getName());
    }
}
