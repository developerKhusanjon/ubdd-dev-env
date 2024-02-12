package uz.ciasev.ubdd_service.utils.deserializer.dict.court;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class CourtStatusCacheDeserializer extends AbstractDictDeserializer<CourtStatus> {

    @Autowired
    public CourtStatusCacheDeserializer(DictionaryService<CourtStatus> courtStatusDictionaryService) {
        super(CourtStatus.class, courtStatusDictionaryService);
    }
}

