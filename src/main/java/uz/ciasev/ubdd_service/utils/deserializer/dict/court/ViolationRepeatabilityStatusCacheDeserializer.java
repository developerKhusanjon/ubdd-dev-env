package uz.ciasev.ubdd_service.utils.deserializer.dict.court;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationRepeatabilityStatus;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ViolationRepeatabilityStatusCacheDeserializer extends AbstractDictDeserializer<ViolationRepeatabilityStatus> {

    @Autowired
    public ViolationRepeatabilityStatusCacheDeserializer(DictionaryService<ViolationRepeatabilityStatus> violationRepeatabilityService) {
        super(ViolationRepeatabilityStatus.class, violationRepeatabilityService);
    }
}

