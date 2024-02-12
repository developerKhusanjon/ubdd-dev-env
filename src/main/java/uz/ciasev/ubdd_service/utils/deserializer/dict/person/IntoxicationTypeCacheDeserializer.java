package uz.ciasev.ubdd_service.utils.deserializer.dict.person;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.person.IntoxicationType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class IntoxicationTypeCacheDeserializer extends AbstractDictDeserializer<IntoxicationType> {

    @Autowired
    public IntoxicationTypeCacheDeserializer(DictionaryService<IntoxicationType> intoxicationTypeService) {
        super(IntoxicationType.class, intoxicationTypeService);
    }
}