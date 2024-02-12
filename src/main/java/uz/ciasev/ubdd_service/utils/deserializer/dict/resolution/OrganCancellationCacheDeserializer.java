package uz.ciasev.ubdd_service.utils.deserializer.dict.resolution;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.resolution.OrganCancellation;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class OrganCancellationCacheDeserializer extends AbstractDictDeserializer<OrganCancellation> {

    @Autowired
    public OrganCancellationCacheDeserializer(DictionaryService<OrganCancellation> service) {
        super(OrganCancellation.class, service);
    }
}