package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.Organ;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class OrganCacheDeserializer extends AbstractDictDeserializer<Organ> {

    @Autowired
    public OrganCacheDeserializer(DictionaryService<Organ> organService) {
        super(Organ.class, organService);
    }
}