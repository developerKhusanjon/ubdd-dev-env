package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class CountryCacheDeserializer extends AbstractDictDeserializer<Country> {

    @Autowired
    public CountryCacheDeserializer(DictionaryService<Country> countryService) {
        super(Country.class, countryService);
    }
}