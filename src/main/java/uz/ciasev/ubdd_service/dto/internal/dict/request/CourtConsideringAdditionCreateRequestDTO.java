package uz.ciasev.ubdd_service.dto.internal.dict.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringAddition;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;
import uz.ciasev.ubdd_service.entity.dict.requests.CourtConsideringAdditionCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
public class CourtConsideringAdditionCreateRequestDTO extends ExternalDictCreateRequestDTO<CourtConsideringAddition>
        implements CourtConsideringAdditionCreateDTOI, DictCreateRequest<CourtConsideringAddition> {

    @NotNull(message = ErrorCode.COURT_CONSIDERING_BASIS_REQUIRED)
    @JsonProperty(value = "courtConsideringBasisId")
    private CourtConsideringBasis courtConsideringBasis;

    @Override
    public void applyToNew(CourtConsideringAddition entity) {
        entity.construct(this);
    }
}
