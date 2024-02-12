package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystem;
import uz.ciasev.ubdd_service.entity.dict.ExternalSystemAlias;
import uz.ciasev.ubdd_service.repository.dict.ExternalSystemRepository;

@Service
@RequiredArgsConstructor
@Getter
public class ExternalSystemDictionaryServiceImpl extends SimpleBackendDictionaryServiceAbstract<ExternalSystem, ExternalSystemAlias>
        implements ExternalSystemDictionaryService {

    private final String subPath = "external-systems";

    private final ExternalSystemRepository repository;
    private final Class<ExternalSystem> entityClass = ExternalSystem.class;

    @Override
    public Class<ExternalSystemAlias> getAliasClass() {
        return ExternalSystemAlias.class;
    }
}
