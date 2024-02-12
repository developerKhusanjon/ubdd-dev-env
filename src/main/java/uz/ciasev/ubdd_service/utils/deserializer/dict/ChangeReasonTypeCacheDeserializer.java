package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ChangeReasonType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ChangeReasonTypeCacheDeserializer extends AbstractDictDeserializer<ChangeReasonType> {

    @Autowired
    public ChangeReasonTypeCacheDeserializer(DictionaryService<ChangeReasonType> bankService) {
        super(ChangeReasonType.class, bankService);
    }
}