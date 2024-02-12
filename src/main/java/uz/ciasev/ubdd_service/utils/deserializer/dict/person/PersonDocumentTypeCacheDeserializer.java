package uz.ciasev.ubdd_service.utils.deserializer.dict.person;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.person.PersonDocumentType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class PersonDocumentTypeCacheDeserializer extends AbstractDictDeserializer<PersonDocumentType> {

    @Autowired
    public PersonDocumentTypeCacheDeserializer(DictionaryService<PersonDocumentType> personDocumentTypeService) {
        super(PersonDocumentType.class, personDocumentTypeService);
    }
}