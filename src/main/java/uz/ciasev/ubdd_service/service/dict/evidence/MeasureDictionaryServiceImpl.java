package uz.ciasev.ubdd_service.service.dict.evidence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.evidence.Measures;
import uz.ciasev.ubdd_service.repository.dict.evidence.MeasureRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class MeasureDictionaryServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<Measures>
        implements MeasureDictionaryService {

    private final String subPath = "measures";

    private final MeasureRepository repository;
    private final Class<Measures> entityClass = Measures.class;
}
