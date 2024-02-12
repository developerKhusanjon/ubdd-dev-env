package uz.ciasev.ubdd_service.utils.deserializer.dict.ubdd;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ubdd.UBDDConfiscatedCategory;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class UBDDConfiscatedCategoryDeserializer extends AbstractDictDeserializer<UBDDConfiscatedCategory> {

    @Autowired
    public UBDDConfiscatedCategoryDeserializer(DictionaryService<UBDDConfiscatedCategory> confiscatedCategoryService) {
        super(UBDDConfiscatedCategory.class, confiscatedCategoryService);
    }
}