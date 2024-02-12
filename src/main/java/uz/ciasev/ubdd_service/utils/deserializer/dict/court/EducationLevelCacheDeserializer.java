package uz.ciasev.ubdd_service.utils.deserializer.dict.court;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.court.EducationLevel;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class EducationLevelCacheDeserializer extends AbstractDictDeserializer<EducationLevel> {

    @Autowired
    public EducationLevelCacheDeserializer(DictionaryService<EducationLevel> educationLevelService) {
        super(EducationLevel.class, educationLevelService);
    }
}

