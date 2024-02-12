package uz.ciasev.ubdd_service.utils.deserializer.dict.resolution;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.resolution.ReasonCancellation;
import uz.ciasev.ubdd_service.service.dict.resolution.ReasonCancellationService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ReasonCancellationCacheDeserializer extends AbstractDictDeserializer<ReasonCancellation> {

    @Autowired
    public ReasonCancellationCacheDeserializer(ReasonCancellationService reasonCancellationService) {
        super(ReasonCancellation.class, reasonCancellationService);
    }
}