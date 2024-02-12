package uz.ciasev.ubdd_service.utils.deserializer.dict.person;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class GenderCacheDeserializer extends AbstractDictDeserializer<Gender> {

    @Autowired
    public GenderCacheDeserializer(DictionaryService<Gender> genderService) {
        super(Gender.class, genderService);
    }
}