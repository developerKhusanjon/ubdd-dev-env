package uz.ciasev.ubdd_service.service.dict.adm_case;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseMovementStatus;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseMovementStatusAlias;
import uz.ciasev.ubdd_service.repository.dict.AdmCaseMovementStatusRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendStatusDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class AdmCaseMovementStatusServiceImpl extends SimpleBackendStatusDictionaryServiceAbstract<AdmCaseMovementStatus, AdmCaseMovementStatusAlias>
        implements AdmCaseMovementStatusService {
    private final String subPath = "adm-case-movement-statuses";

    private final AdmCaseMovementStatusRepository repository;
    private final Class<AdmCaseMovementStatus> entityClass = AdmCaseMovementStatus.class;

    @Override
    public Class<AdmCaseMovementStatusAlias> getAliasClass() {
        return AdmCaseMovementStatusAlias.class;
    }
}
