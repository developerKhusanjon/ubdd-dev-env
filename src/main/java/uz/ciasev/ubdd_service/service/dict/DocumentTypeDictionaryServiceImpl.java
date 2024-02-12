package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DocumentTypeResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.entity.dict.DocumentTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.DocumentTypeRepository;

@Getter
@Service
@RequiredArgsConstructor
public class DocumentTypeDictionaryServiceImpl extends SimpleAliasedDictionaryServiceAbstract<DocumentType, DocumentTypeAlias> implements DocumentTypeDictionaryService {

    private final String subPath = "document-types";

    private final Class<DocumentType> entityClass = DocumentType.class;
    private final DocumentTypeRepository repository;

    @Override
    public DocumentTypeResponseDTO buildResponseDTO(DocumentType entity) {
        return new DocumentTypeResponseDTO(entity);
    }

    @Override
    public DocumentTypeResponseDTO buildListResponseDTO(DocumentType entity) {return new DocumentTypeResponseDTO(entity);}

    @Override
    public Class<DocumentTypeAlias> getAliasClass() {
        return DocumentTypeAlias.class;
    }
}
