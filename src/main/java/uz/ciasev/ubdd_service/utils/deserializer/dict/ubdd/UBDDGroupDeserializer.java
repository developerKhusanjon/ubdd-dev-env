package uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDGroup;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class UBDDGroupDeserializer extends AbstractDictDeserializer<UBDDGroup> {

    @Autowired
    public UBDDGroupDeserializer(DictionaryService<UBDDGroup> groupService) {
        super(UBDDGroup.class, groupService);
    }
}