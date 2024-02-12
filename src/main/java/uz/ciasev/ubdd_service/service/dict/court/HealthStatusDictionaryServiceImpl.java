package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.HealthStatus;
import uz.ciasev.ubdd_service.repository.dict.court.HealthStatusRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class HealthStatusDictionaryServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<HealthStatus>
        implements HealthStatusDictionaryService {

    private final String subPath = "health-status";

    private final HealthStatusRepository repository;
    private final Class<HealthStatus> entityClass = HealthStatus.class;
}
