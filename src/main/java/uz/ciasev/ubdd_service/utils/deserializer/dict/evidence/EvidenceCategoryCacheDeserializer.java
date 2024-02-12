package uz.ciasev.ubdd_service.utils.deserializer.dict.evidence;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceCategory;
import uz.ciasev.ubdd_service.service.dict.evidence.EvidenceCategoryDictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class EvidenceCategoryCacheDeserializer extends AbstractDictDeserializer<EvidenceCategory> {

    @Autowired
    public EvidenceCategoryCacheDeserializer(EvidenceCategoryDictionaryService evidenceCategoryService) {
        super(EvidenceCategory.class, evidenceCategoryService);
    }
}