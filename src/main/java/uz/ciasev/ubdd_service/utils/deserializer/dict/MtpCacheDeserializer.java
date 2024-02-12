package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.Mtp;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class MtpCacheDeserializer extends AbstractDictDeserializer<Mtp> {

    @Autowired
    public MtpCacheDeserializer(DictionaryService<Mtp> mtpService) {
        super(Mtp.class, mtpService);
    }
}