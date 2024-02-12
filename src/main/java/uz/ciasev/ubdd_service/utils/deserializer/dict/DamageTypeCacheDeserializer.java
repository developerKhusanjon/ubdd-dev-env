package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.DamageType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class DamageTypeCacheDeserializer extends AbstractDictDeserializer<DamageType> {

    @Autowired
    public DamageTypeCacheDeserializer(DictionaryService<DamageType> damageTypeService) {
        super(DamageType.class, damageTypeService);
    }
}