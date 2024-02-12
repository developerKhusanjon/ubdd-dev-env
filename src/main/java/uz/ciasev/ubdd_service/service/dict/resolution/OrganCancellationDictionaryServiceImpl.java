package uz.ciasev.ubdd_service.service.dict.resolution;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.resolution.OrganCancellation;
import uz.ciasev.ubdd_service.entity.dict.resolution.OrganCancellationAlias;
import uz.ciasev.ubdd_service.repository.dict.resolution.OrganCancellationResolutionRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleAliasedDictionaryServiceAbstract;

@Getter
@Service
@RequiredArgsConstructor
public class OrganCancellationDictionaryServiceImpl extends SimpleAliasedDictionaryServiceAbstract<OrganCancellation, OrganCancellationAlias> implements OrganCancellationDictionaryService {

    private final String subPath = "organs-cancellation";

    private final Class<OrganCancellation> entityClass = OrganCancellation.class;
    private final OrganCancellationResolutionRepository repository;

    @Override
    public Class<OrganCancellationAlias> getAliasClass() {
        return OrganCancellationAlias.class;
    }
}
