package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.admcase.DeclineReason;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class DeclineReasonDeserializer extends AbstractDictDeserializer<DeclineReason> {

    @Autowired
    public DeclineReasonDeserializer(DictionaryService<DeclineReason> declineReasonService) {
        super(DeclineReason.class, declineReasonService);
    }
}