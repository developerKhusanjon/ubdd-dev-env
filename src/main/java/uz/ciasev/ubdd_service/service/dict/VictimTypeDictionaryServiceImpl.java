package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.dict.VictimTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.VictimTypeRepository;

@Service
@RequiredArgsConstructor
@Getter
public class VictimTypeDictionaryServiceImpl extends SimpleBackendDictionaryServiceAbstract<VictimType, VictimTypeAlias>
        implements VictimTypeDictionaryService {

    private final String subPath = "victim-types";

    private final VictimTypeRepository repository;
    private final Class<VictimType> entityClass = VictimType.class;

    @Override
    public Class<VictimTypeAlias> getAliasClass() {
        return VictimTypeAlias.class;
    }
}
