package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;
import uz.ciasev.ubdd_service.entity.dict.requests.CourtConsideringBasisCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class CourtConsideringBasisCreateRequestDTO extends CourtConsideringBasisUpdateRequestDTO implements DictCreateRequest<CourtConsideringBasis>, CourtConsideringBasisCreateDTOI {

    @NotNull(message = ErrorCode.ID_REQUIRED)
    private Long id;

    @Override
    public void applyToNew(CourtConsideringBasis entity) {
        entity.construct(this);
    }
}
