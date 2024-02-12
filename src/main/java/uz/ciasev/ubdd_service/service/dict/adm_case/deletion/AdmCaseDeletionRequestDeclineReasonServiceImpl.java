package uz.ciasev.ubdd_service.service.dict.adm_case.deletion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionRequestDeclineReason;
import uz.ciasev.ubdd_service.repository.dict.admcase.deletion.AdmCaseDeletionDeclineReasonRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class AdmCaseDeletionRequestDeclineReasonServiceImpl extends SimpleEmiDictionaryServiceAbstract<AdmCaseDeletionRequestDeclineReason>
        implements AdmCaseDeletionRequestDeclineReasonService {

    private final String subPath = "adm-deletion-decline-reasons";

    private final Class<AdmCaseDeletionRequestDeclineReason> entityClass = AdmCaseDeletionRequestDeclineReason.class;
    private final AdmCaseDeletionDeclineReasonRepository repository;
}
