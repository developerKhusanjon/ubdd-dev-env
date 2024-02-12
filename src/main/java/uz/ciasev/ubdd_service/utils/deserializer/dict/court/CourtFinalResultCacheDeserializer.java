package uz.ciasev.ubdd_service.utils.deserializer.dict.court;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.court.CourtFinalResult;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class CourtFinalResultCacheDeserializer extends AbstractDictDeserializer<CourtFinalResult> {

    @Autowired
    public CourtFinalResultCacheDeserializer(DictionaryService<CourtFinalResult> service) {
        super(CourtFinalResult.class, service);
    }
}

