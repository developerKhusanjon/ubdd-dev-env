package uz.ciasev.ubdd_service.utils.deserializer.dict.violation_event;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.violation_event.ViolationEventAnnulmentReason;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ViolationEventAnnulmentReasonCacheDeserializer extends AbstractDictDeserializer<ViolationEventAnnulmentReason> {

    @Autowired
    public ViolationEventAnnulmentReasonCacheDeserializer(DictionaryService<ViolationEventAnnulmentReason> service) {
        super(ViolationEventAnnulmentReason.class, service);
    }
}