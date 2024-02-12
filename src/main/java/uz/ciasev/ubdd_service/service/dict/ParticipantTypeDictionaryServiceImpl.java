package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.ParticipantType;
import uz.ciasev.ubdd_service.entity.dict.ParticipantTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.ParticipantTypeRepository;

@Getter
@Service
@RequiredArgsConstructor
public class ParticipantTypeDictionaryServiceImpl extends SimpleAliasedDictionaryServiceAbstract<ParticipantType, ParticipantTypeAlias> implements ParticipantTypeDictionaryService {

    private final String subPath = "participant-types";

    private final ParticipantTypeRepository repository;
    private final Class<ParticipantType> entityClass = ParticipantType.class;

    @Override
    public Class<ParticipantTypeAlias> getAliasClass() {
        return ParticipantTypeAlias.class;
    }
}
