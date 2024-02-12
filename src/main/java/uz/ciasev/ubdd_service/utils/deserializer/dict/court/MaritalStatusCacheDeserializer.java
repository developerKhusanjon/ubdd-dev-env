package uz.ciasev.ubdd_service.utils.deserializer.dict.court;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.court.MaritalStatus;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class MaritalStatusCacheDeserializer extends AbstractDictDeserializer<MaritalStatus> {

    @Autowired
    public MaritalStatusCacheDeserializer(DictionaryService<MaritalStatus> materialStatusService) {
        super(MaritalStatus.class, materialStatusService);
    }
}

