package uz.ciasev.ubdd_service.service.dict.evidence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.repository.dict.evidence.CurrencyRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleExternalIdDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class CurrencyDictionaryServiceImpl extends SimpleExternalIdDictionaryServiceAbstract<Currency>
        implements CurrencyDictionaryService {

    private final String subPath = "currencies";

    private final CurrencyRepository repository;
    private final Class<Currency> entityClass = Currency.class;
}
