package uz.ciasev.ubdd_service.service.dict.violation_event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.violation_event.ViolationEventResultType;
import uz.ciasev.ubdd_service.entity.dict.violation_event.ViolationEventResultTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.violation_event.ViolationEventResultTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class ViolationEventResultTypeDictionaryServiceImpl extends SimpleBackendDictionaryServiceAbstract<ViolationEventResultType, ViolationEventResultTypeAlias>
        implements ViolationEventResultTypeDictionaryService {

    private final String subPath = "violation-event-result-types";

    private final ViolationEventResultTypeRepository repository;
    private final Class<ViolationEventResultType> entityClass = ViolationEventResultType.class;

    @Override
    public Class<ViolationEventResultTypeAlias> getAliasClass() {
        return ViolationEventResultTypeAlias.class;
    }
}
