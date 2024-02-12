package uz.ciasev.ubdd_service.utils.deserializer.dict.court;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringAddition;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class CourtConsideringAdditionCacheDeserializer extends AbstractDictDeserializer<CourtConsideringAddition> {

    @Autowired
    public CourtConsideringAdditionCacheDeserializer(DictionaryService<CourtConsideringAddition> courtConsideringBasisAdditionallyService) {
        super(CourtConsideringAddition.class, courtConsideringBasisAdditionallyService);
    }
}

