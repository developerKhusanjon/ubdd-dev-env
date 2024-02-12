package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.MaritalStatus;
import uz.ciasev.ubdd_service.repository.dict.court.MaritalStatusRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class MaritalStatusDictionaryServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<MaritalStatus>
        implements MaritalStatusDictionaryService {

    private final String subPath = "marital-status";

    private final MaritalStatusRepository repository;
    private final Class<MaritalStatus> entityClass = MaritalStatus.class;
}
