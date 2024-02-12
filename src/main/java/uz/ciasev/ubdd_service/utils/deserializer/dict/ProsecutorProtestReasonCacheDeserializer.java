package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.prosecutor.ProsecutorProtestReason;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ProsecutorProtestReasonCacheDeserializer extends AbstractDictDeserializer<ProsecutorProtestReason> {

    @Autowired
    public ProsecutorProtestReasonCacheDeserializer(DictionaryService<ProsecutorProtestReason> service) {
        super(ProsecutorProtestReason.class, service);
    }
}
