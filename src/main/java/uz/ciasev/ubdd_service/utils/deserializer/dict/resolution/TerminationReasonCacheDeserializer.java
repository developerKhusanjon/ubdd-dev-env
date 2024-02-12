package uz.ciasev.ubdd_service.utils.deserializer.dict.resolution;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.resolution.TerminationReason;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class TerminationReasonCacheDeserializer extends AbstractDictDeserializer<TerminationReason> {

    @Autowired
    public TerminationReasonCacheDeserializer(DictionaryService<TerminationReason> terminationReasonService) {
        super(TerminationReason.class, terminationReasonService);
    }
}