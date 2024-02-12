package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.DocumentType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class DocumentTypeCacheDeserializer extends AbstractDictDeserializer<DocumentType> {

    @Autowired
    public DocumentTypeCacheDeserializer(DictionaryService<DocumentType> documentTypeService) {
        super(DocumentType.class, documentTypeService);
    }
}