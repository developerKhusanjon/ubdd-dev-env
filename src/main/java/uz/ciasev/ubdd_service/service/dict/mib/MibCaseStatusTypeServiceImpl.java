package uz.ciasev.ubdd_service.service.dict.mib;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatusAlias;
import uz.ciasev.ubdd_service.entity.dict.mib.MibCaseStatusType;
import uz.ciasev.ubdd_service.repository.dict.mib.MibCaseStatusTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendStatusDictionaryServiceAbstract;


@Service
@RequiredArgsConstructor
@Getter
public class MibCaseStatusTypeServiceImpl extends SimpleBackendStatusDictionaryServiceAbstract<MibCaseStatusType, MibCaseStatusAlias>
        implements MibCaseStatusTypeService {
    private final String subPath = "mib-case-status-types";

    private final MibCaseStatusTypeRepository repository;
    private final Class<MibCaseStatusType> entityClass = MibCaseStatusType.class;

    @Override
    public Class<MibCaseStatusAlias> getAliasClass() {
        return MibCaseStatusAlias.class;
    }
}
