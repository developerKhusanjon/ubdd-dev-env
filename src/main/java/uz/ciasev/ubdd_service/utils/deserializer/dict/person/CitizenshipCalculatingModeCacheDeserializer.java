package uz.ciasev.ubdd_service.utils.deserializer.dict.person;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipCalculatingMode;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class CitizenshipCalculatingModeCacheDeserializer extends AbstractDictDeserializer<CitizenshipCalculatingMode> {

    @Autowired
    public CitizenshipCalculatingModeCacheDeserializer(DictionaryService<CitizenshipCalculatingMode> service) {
        super(CitizenshipCalculatingMode.class, service);
    }
}