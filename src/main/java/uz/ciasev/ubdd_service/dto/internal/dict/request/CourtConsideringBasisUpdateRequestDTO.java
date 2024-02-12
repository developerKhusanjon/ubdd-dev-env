package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;
import uz.ciasev.ubdd_service.entity.dict.requests.CourtConsideringBasisUpdateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class CourtConsideringBasisUpdateRequestDTO extends ExternalDictUpdateRequestDTO<CourtConsideringBasis> implements CourtConsideringBasisUpdateDTOI {

    @NotNull(message = ErrorCode.HAS_ADDITIONS_REQUIRED)
    private Boolean hasAdditions;

    @Override
    public void applyToOld(CourtConsideringBasis entity) {
        entity.update(this);
    }
}
