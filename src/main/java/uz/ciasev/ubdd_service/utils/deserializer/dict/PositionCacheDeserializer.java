package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.user.Position;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class PositionCacheDeserializer extends AbstractDictDeserializer<Position> {

    @Autowired
    public PositionCacheDeserializer(DictionaryService<Position> positionService) {
        super(Position.class, positionService);
    }
}