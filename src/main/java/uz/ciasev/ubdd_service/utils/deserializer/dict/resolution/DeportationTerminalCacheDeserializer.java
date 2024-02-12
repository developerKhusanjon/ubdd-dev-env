package uz.ciasev.ubdd_service.utils.deserializer.dict.resolution;

import uz.ciasev.ubdd_service.entity.dict.resolution.DeportationTerminal;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class DeportationTerminalCacheDeserializer extends AbstractDictDeserializer<DeportationTerminal> {

    public DeportationTerminalCacheDeserializer(DictionaryService<DeportationTerminal> service) {
        super(DeportationTerminal.class, service);
    }
}
