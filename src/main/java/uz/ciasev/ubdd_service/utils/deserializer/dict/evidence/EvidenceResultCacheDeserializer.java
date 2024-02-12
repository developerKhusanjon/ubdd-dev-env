package uz.ciasev.ubdd_service.utils.deserializer.dict.evidence;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceResult;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class EvidenceResultCacheDeserializer extends AbstractDictDeserializer<EvidenceResult> {

    @Autowired
    public EvidenceResultCacheDeserializer(DictionaryService<EvidenceResult> evidenceResultService) {
        super(EvidenceResult.class, evidenceResultService);
    }
}