package uz.ciasev.ubdd_service.dto.internal.trans.request.court;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.person.Nationality;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.SimpleTransEntityCreateDTOI;
import uz.ciasev.ubdd_service.entity.trans.court.CourtTransNationality;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.trans.TransEntityCreateRequest;

import javax.validation.constraints.NotNull;

@Data
public class CourtTransNationalityCreateRequestDTO implements SimpleTransEntityCreateDTOI<Nationality>, TransEntityCreateRequest<CourtTransNationality> {

    @NotNull(message = ErrorCode.EXTERNAL_ID_REQUIRED)
    private Long externalId;

    @NotNull(message = ErrorCode.INTERNAL_ID_REQUIRED)
    @JsonProperty(value = "internalId")
    private Nationality internal;

    @Override
    public void applyToNew(CourtTransNationality entity) {
        entity.construct(this);
    }
}
