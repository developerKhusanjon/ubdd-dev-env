package uz.ciasev.ubdd_service.utils.deserializer.dict.person;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.person.Nationality;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class NationalityCacheDeserializer extends AbstractDictDeserializer<Nationality> {

    @Autowired
    public NationalityCacheDeserializer(DictionaryService<Nationality> nationalityService) {
        super(Nationality.class, nationalityService);
    }
}