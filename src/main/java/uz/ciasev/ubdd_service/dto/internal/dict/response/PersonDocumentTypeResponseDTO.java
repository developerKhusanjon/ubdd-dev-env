package uz.ciasev.ubdd_service.dto.internal.dict.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;

@Getter
public class PersonDocumentTypeResponseDTO extends DictResponseDTO {

    private final Boolean isBiometric;
    private final Long citizenshipCalculatingModeId;

    public PersonDocumentTypeResponseDTO(PersonDocumentType entity) {
        super(entity);
        this.isBiometric = entity.getIsBiometric();
        this.citizenshipCalculatingModeId = entity.getCitizenshipCalculatingModeId();
    }
}
