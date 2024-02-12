package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.court.ViolationPlaceType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ViolationPlaceTypeCacheDeserializer extends AbstractDictDeserializer<ViolationPlaceType> {

    @Autowired
    public ViolationPlaceTypeCacheDeserializer(DictionaryService<ViolationPlaceType> violationPlaceTypeService) {
        super(ViolationPlaceType.class, violationPlaceTypeService);
    }
}

