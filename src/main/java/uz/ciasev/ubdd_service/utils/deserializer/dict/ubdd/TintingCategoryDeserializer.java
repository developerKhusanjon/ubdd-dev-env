package uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ubdd.TintingCategory;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class TintingCategoryDeserializer extends AbstractDictDeserializer<TintingCategory> {

    @Autowired
    public TintingCategoryDeserializer(DictionaryService<TintingCategory> tintingCategoryService) {
        super(TintingCategory.class, tintingCategoryService);
    }
}