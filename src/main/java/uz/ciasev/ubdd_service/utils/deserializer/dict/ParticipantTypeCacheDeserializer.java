package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.ParticipantType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class ParticipantTypeCacheDeserializer extends AbstractDictDeserializer<ParticipantType> {

    @Autowired
    public ParticipantTypeCacheDeserializer(DictionaryService<ParticipantType> participantTypeService) {
        super(ParticipantType.class, participantTypeService);
    }
}