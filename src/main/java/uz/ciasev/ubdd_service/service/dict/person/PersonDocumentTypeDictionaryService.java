package uz.ciasev.ubdd_service.service.dict.person;

import uz.ciasev.ubdd_service.dto.internal.dict.request.PersonDocumentTypeRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.service.dict.DictionaryCRUDService;
import uz.ciasev.ubdd_service.service.dict.UnknownValueByIdDictionaryService;

public interface PersonDocumentTypeDictionaryService
        extends DictionaryCRUDService<PersonDocumentType, PersonDocumentTypeRequestDTO, PersonDocumentTypeRequestDTO>, UnknownValueByIdDictionaryService<PersonDocumentType> {
}