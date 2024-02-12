package uz.ciasev.ubdd_service.utils.deserializer.dict.court;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.court.HealthStatus;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class HealthStatusCacheDeserializer extends AbstractDictDeserializer<HealthStatus> {

    @Autowired
    public HealthStatusCacheDeserializer(DictionaryService<HealthStatus> healthStatusService) {
        super(HealthStatus.class, healthStatusService);
    }
}

