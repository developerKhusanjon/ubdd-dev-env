package uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDImpound;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class UBDDImpoundDeserializer extends AbstractDictDeserializer<UBDDImpound> {

    @Autowired
    public UBDDImpoundDeserializer(DictionaryService<UBDDImpound> impoundService) {
        super(UBDDImpound.class, impoundService);
    }
}