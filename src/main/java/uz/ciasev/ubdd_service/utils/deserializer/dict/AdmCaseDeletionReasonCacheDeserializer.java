package uz.ciasev.ubdd_service.utils.deserializer.dict;

import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionReason;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class AdmCaseDeletionReasonCacheDeserializer extends AbstractDictDeserializer<AdmCaseDeletionReason> {

    public AdmCaseDeletionReasonCacheDeserializer(DictionaryService<AdmCaseDeletionReason> service) {
        super(AdmCaseDeletionReason.class, service);
    }
}
