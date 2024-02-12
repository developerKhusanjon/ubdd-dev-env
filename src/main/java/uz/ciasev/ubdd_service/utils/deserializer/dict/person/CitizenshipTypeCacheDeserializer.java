package uz.ciasev.ubdd_service.utils.deserializer.dict.person;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class CitizenshipTypeCacheDeserializer extends AbstractDictDeserializer<CitizenshipType> {

    @Autowired
    public CitizenshipTypeCacheDeserializer(DictionaryService<CitizenshipType> citizenshipTypeService) {
        super(CitizenshipType.class, citizenshipTypeService);
    }
}