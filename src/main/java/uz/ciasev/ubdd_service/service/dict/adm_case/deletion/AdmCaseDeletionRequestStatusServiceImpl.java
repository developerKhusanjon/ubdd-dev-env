package uz.ciasev.ubdd_service.service.dict.adm_case.deletion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestStatus;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestStatusAlias;
import uz.ciasev.ubdd_service.repository.dict.admcase.deletion.AdmCaseDeletionRequestStatusRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendStatusDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class AdmCaseDeletionRequestStatusServiceImpl extends SimpleBackendStatusDictionaryServiceAbstract<AdmCaseDeletionRequestStatus, AdmCaseDeletionRequestStatusAlias>
        implements AdmCaseDeletionRequestStatusService {

    private final String subPath = "adm-deletion-request-statuses";

    private final AdmCaseDeletionRequestStatusRepository repository;
    private final Class<AdmCaseDeletionRequestStatus> entityClass = AdmCaseDeletionRequestStatus.class;

    @Override
    public Class<AdmCaseDeletionRequestStatusAlias> getAliasClass() {
        return AdmCaseDeletionRequestStatusAlias.class;
    }
}
