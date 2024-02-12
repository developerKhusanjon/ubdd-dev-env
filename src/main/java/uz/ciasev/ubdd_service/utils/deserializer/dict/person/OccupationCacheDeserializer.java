package uz.ciasev.ubdd_service.utils.deserializer.dict.person;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.person.Occupation;
import uz.ciasev.ubdd_service.service.dict.person.OccupationDictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class OccupationCacheDeserializer extends AbstractDictDeserializer<Occupation> {

    @Autowired
    public OccupationCacheDeserializer(OccupationDictionaryService occupationService) {
        super(Occupation.class, occupationService);
    }
}