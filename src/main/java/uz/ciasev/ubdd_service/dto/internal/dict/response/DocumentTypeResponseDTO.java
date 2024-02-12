package uz.ciasev.ubdd_service.dto.internal.dict.response;

import lombok.Getter;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

@Getter
public class DocumentTypeResponseDTO extends AliasedDictResponseDTO {

    private final MultiLanguage description;

    public DocumentTypeResponseDTO(DocumentType entity) {
        super(entity);
        this.description = entity.getDescription();
    }
}
