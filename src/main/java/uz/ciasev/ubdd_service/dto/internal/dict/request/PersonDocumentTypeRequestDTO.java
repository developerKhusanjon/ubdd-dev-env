package uz.ciasev.ubdd_service.dto.internal.dict.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipCalculatingMode;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.entity.dict.requests.PersonDocumentTypeDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Data
public class PersonDocumentTypeRequestDTO extends BaseDictRequestDTO implements PersonDocumentTypeDTOI, DictCreateRequest<PersonDocumentType>, DictUpdateRequest<PersonDocumentType> {

    @NotNull(message = ErrorCode.CITIZENSHIP_CALCULATING_MODE_REQUIRED)
    @JsonProperty("citizenshipCalculatingModeId")
    private CitizenshipCalculatingMode citizenshipCalculatingMode;

    @NotNull(message = ErrorCode.IS_BIOMETRIC_REQUIRED)
    private Boolean isBiometric;

    @Override
    public void applyToNew(PersonDocumentType entity) {
        entity.construct(this);
    }

    @Override
    public void applyToOld(PersonDocumentType entity) {
        entity.update(this);
    }
}
