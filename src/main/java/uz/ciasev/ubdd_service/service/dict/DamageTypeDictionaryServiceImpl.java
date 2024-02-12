package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.DamageType;
import uz.ciasev.ubdd_service.entity.dict.DamageTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.DamageTypeRepository;

@Getter
@Service
@RequiredArgsConstructor
public class DamageTypeDictionaryServiceImpl extends SimpleBackendDictionaryServiceAbstract<DamageType, DamageTypeAlias> implements DamageTypeDictionaryService {

    private final String subPath = "damage-types";

    private final DamageTypeRepository repository;
    private final Class<DamageType> entityClass = DamageType.class;

    @Override
    public Class<DamageTypeAlias> getAliasClass() {
        return DamageTypeAlias.class;
    }
}
