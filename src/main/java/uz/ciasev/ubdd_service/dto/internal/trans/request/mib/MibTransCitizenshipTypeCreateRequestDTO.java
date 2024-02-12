package uz.ciasev.ubdd_service.dto.internal.trans.request.mib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.SimpleTransEntityCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.mib.MibTransCitizenshipType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
public class MibTransCitizenshipTypeCreateRequestDTO implements SimpleTransEntityCreateDTOI<CitizenshipType>, TransEntityCreateRequest<MibTransCitizenshipType> {

    @NotNull(message = ErrorCode.EXTERNAL_ID_REQUIRED)
    private Long externalId;

    @NotNull(message = ErrorCode.INTERNAL_ID_REQUIRED)
    @JsonProperty(value = "internalId")
    private CitizenshipType internal;

    @Override
    public void applyToNew(MibTransCitizenshipType entity) {
        entity.construct(this);
    }
}
