package uz.ciasev.ubdd_service.utils.deserializer.dict.person;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.person.AgeCategory;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class AgeCategoryCacheDeserializer extends AbstractDictDeserializer<AgeCategory> {

    @Autowired
    public AgeCategoryCacheDeserializer(DictionaryService<AgeCategory> ageCategoryService) {
        super(AgeCategory.class, ageCategoryService);
    }
}