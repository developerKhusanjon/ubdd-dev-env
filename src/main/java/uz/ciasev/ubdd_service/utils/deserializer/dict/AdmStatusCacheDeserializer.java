package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.service.status.AdmStatusDictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractEntityDeserializer;

public class AdmStatusCacheDeserializer extends AbstractEntityDeserializer<AdmStatus> {

    @Autowired
    public AdmStatusCacheDeserializer(AdmStatusDictionaryService admStatusService) {
        super(AdmStatus.class, admStatusService::getById);
    }
}