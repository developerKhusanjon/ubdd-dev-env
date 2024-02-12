package uz.ciasev.ubdd_service.service.dict.resolution;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.resolution.CriminalCaseTransferResultType;
import uz.ciasev.ubdd_service.repository.dict.resolution.CriminalCaseTransferResultTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class CriminalCaseTransferResultTypeDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<CriminalCaseTransferResultType>
        implements CriminalCaseTransferResultTypeDictionaryService {
    private final String subPath = "criminal-case-transfer-result-types";

    private final CriminalCaseTransferResultTypeRepository repository;
    private final Class<CriminalCaseTransferResultType> entityClass = CriminalCaseTransferResultType.class;
}
