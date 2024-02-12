package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestDeclineReason;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class AdmCaseDeletionDeclineReasonCacheDeserializer extends AbstractDictDeserializer<AdmCaseDeletionRequestDeclineReason> {

    @Autowired
    public AdmCaseDeletionDeclineReasonCacheDeserializer(DictionaryService<AdmCaseDeletionRequestDeclineReason> service) {
        super(AdmCaseDeletionRequestDeclineReason.class, service);
    }
}
