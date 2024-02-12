package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.Region;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class RegionCacheDeserializer extends AbstractDictDeserializer<Region> {

    @Autowired
    public RegionCacheDeserializer(DictionaryService<Region> regionService) {
        super(Region.class, regionService);
    }
}