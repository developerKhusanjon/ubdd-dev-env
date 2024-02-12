package uz.ciasev.ubdd_service.service.dict.person;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.PersonDocumentTypeRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.dict.response.PersonDocumentTypeResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.repository.dict.person.PersonDocumentTypeRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryCRUDService;

@Getter
@Service
@RequiredArgsConstructor
public class PersonDocumentTypeDictionaryServiceImpl
        extends AbstractDictionaryCRUDService<PersonDocumentType, PersonDocumentTypeRequestDTO, PersonDocumentTypeRequestDTO>
        implements PersonDocumentTypeDictionaryService {

    private final Long unknownId = 999L;

    private final String subPath = "person-document-types";
    private final TypeReference<PersonDocumentTypeRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<PersonDocumentTypeRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final Class<PersonDocumentType> entityClass = PersonDocumentType.class;
    private final PersonDocumentTypeRepository repository;

    @Override
    public Object buildResponseDTO(PersonDocumentType entity) {
        return new PersonDocumentTypeResponseDTO(entity);
    }

}
