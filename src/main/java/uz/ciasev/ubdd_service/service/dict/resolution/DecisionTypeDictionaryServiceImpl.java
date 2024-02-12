package uz.ciasev.ubdd_service.service.dict.resolution;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionType;
import uz.ciasev.ubdd_service.entity.dict.resolution.DecisionTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.resolution.DecisionTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class DecisionTypeDictionaryServiceImpl extends SimpleBackendDictionaryServiceAbstract<DecisionType, DecisionTypeAlias>
        implements DecisionTypeDictionaryService {

    private final String subPath = "decision-types";

    private final DecisionTypeRepository repository;
    private final Class<DecisionType> entityClass = DecisionType.class;

    @Override
    public Class<DecisionTypeAlias> getAliasClass() {
        return DecisionTypeAlias.class;
    }
}
