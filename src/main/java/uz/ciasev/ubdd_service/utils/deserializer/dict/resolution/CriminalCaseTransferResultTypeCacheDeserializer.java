package uz.ciasev.ubdd_service.utils.deserializer.dict.resolution;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.resolution.CriminalCaseTransferResultType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class CriminalCaseTransferResultTypeCacheDeserializer extends AbstractDictDeserializer<CriminalCaseTransferResultType> {

    @Autowired
    public CriminalCaseTransferResultTypeCacheDeserializer(DictionaryService<CriminalCaseTransferResultType> service) {
        super(CriminalCaseTransferResultType.class, service);
    }
}