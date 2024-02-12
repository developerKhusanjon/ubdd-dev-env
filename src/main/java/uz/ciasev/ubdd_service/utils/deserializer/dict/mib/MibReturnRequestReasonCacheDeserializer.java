package uz.ciasev.ubdd_service.utils.deserializer.dict.mib;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.mib.MibReturnRequestReason;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class MibReturnRequestReasonCacheDeserializer extends AbstractDictDeserializer<MibReturnRequestReason> {

    @Autowired
    public MibReturnRequestReasonCacheDeserializer(DictionaryService<MibReturnRequestReason> service) {
        super(MibReturnRequestReason.class, service);
    }
}