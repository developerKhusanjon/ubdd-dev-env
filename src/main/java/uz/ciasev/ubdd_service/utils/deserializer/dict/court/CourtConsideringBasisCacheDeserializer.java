package uz.ciasev.ubdd_service.utils.deserializer.dict.court;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.court.CourtConsideringBasis;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class CourtConsideringBasisCacheDeserializer extends AbstractDictDeserializer<CourtConsideringBasis> {

    @Autowired
    public CourtConsideringBasisCacheDeserializer(DictionaryService<CourtConsideringBasis> courtConsideringBasisService) {
        super(CourtConsideringBasis.class, courtConsideringBasisService);
    }
}

