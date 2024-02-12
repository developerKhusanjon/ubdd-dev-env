package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.resolution.ManualPaymentSourceType;
import uz.ciasev.ubdd_service.entity.dict.resolution.ManualPaymentSourceTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.resolution.ManualPaymentSourceTypeRepository;

@Service
@RequiredArgsConstructor
@Getter
public class ManualPaymentSourceTypeDictionaryServiceImpl extends SimpleBackendDictionaryServiceAbstract<ManualPaymentSourceType, ManualPaymentSourceTypeAlias>
        implements ManualPaymentSourceTypeDictionaryService {

    private final String subPath = "manual-payments-source-types";

    private final ManualPaymentSourceTypeRepository repository;
    private final Class<ManualPaymentSourceType> entityClass = ManualPaymentSourceType.class;

    @Override
    public Class<ManualPaymentSourceTypeAlias> getAliasClass() {
        return ManualPaymentSourceTypeAlias.class;
    }
}
