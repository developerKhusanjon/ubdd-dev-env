package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.District;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class DistrictCacheDeserializer extends AbstractDictDeserializer<District> {

    @Autowired
    public DistrictCacheDeserializer(DictionaryService<District> districtService) {
        super(District.class, districtService);
    }
}