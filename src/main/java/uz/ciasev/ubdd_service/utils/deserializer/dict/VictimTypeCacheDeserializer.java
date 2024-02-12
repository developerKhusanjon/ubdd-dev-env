package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class VictimTypeCacheDeserializer extends AbstractDictDeserializer<VictimType> {

    @Autowired
    public VictimTypeCacheDeserializer(DictionaryService<VictimType> victimTypeService) {
        super(VictimType.class, victimTypeService);
    }
}