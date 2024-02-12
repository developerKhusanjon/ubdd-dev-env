package uz.ciasev.ubdd_service.utils.deserializer;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;

public abstract class AbstractDictDeserializer<T> extends AbstractEntityDeserializer<T> {

    @Autowired
    public AbstractDictDeserializer(Class<T> vc, DictionaryService<T> abstractDictionaryService) {
        super(vc, abstractDictionaryService::getById);
    }
}