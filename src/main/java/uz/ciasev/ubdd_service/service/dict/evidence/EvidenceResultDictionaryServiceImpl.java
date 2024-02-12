package uz.ciasev.ubdd_service.service.dict.evidence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.evidence.EvidenceResult;
import uz.ciasev.ubdd_service.repository.dict.evidence.EvidenceResultRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class EvidenceResultDictionaryServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<EvidenceResult>
        implements EvidenceResultDictionaryService {

    private final String subPath = "evidence-results";

    private final EvidenceResultRepository repository;
    private final Class<EvidenceResult> entityClass = EvidenceResult.class;
}