package uz.ciasev.ubdd_service.utils.deserializer.dict.resolution;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.resolution.ArrestPlaceType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ArrestPlaceTypeCacheDeserializer extends AbstractDictDeserializer<ArrestPlaceType> {

    @Autowired
    public ArrestPlaceTypeCacheDeserializer(DictionaryService<ArrestPlaceType> arrestPlaceTypeService) {
        super(ArrestPlaceType.class, arrestPlaceTypeService);
    }
}
