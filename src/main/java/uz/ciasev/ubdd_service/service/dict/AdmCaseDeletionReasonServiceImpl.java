package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.admcase.AdmCaseDeletionReason;
import uz.ciasev.ubdd_service.repository.dict.AdmCaseDeletionReasonRepository;

@Getter
@Service
@RequiredArgsConstructor
public class AdmCaseDeletionReasonServiceImpl extends SimpleEmiDictionaryServiceAbstract<AdmCaseDeletionReason> implements AdmCaseDeletionReasonService {

    private final String subPath = "adm-deletion-reasons";

    private final Class<AdmCaseDeletionReason> entityClass = AdmCaseDeletionReason.class;
    private final AdmCaseDeletionReasonRepository repository;
}
