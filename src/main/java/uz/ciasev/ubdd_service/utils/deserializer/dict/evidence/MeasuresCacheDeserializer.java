package uz.ciasev.ubdd_service.utils.deserializer.dict.evidence;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class MeasuresCacheDeserializer extends AbstractDictDeserializer<Measures> {

    @Autowired
    public MeasuresCacheDeserializer(DictionaryService<Measures> measureService) {
        super(Measures.class, measureService);
    }
}