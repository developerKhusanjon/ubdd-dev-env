package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystem;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ExternalSystemCacheDeserializer extends AbstractDictDeserializer<ExternalSystem> {

    @Autowired
    public ExternalSystemCacheDeserializer(DictionaryService<ExternalSystem> externalSystemService) {
        super(ExternalSystem.class, externalSystemService);
    }
}