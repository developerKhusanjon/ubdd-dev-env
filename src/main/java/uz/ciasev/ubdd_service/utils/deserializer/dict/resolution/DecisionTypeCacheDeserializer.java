package uz.ciasev.ubdd_service.utils.deserializer.dict.resolution;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class DecisionTypeCacheDeserializer extends AbstractDictDeserializer<DecisionType> {

    @Autowired
    public DecisionTypeCacheDeserializer(DictionaryService<DecisionType> decisionTypeService) {
        super(DecisionType.class, decisionTypeService);
    }
}