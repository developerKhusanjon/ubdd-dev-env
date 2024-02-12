package uz.ciasev.ubdd_service.utils.deserializer.dict.evidence;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class CurrencyCacheDeserializer extends AbstractDictDeserializer<Currency> {

    @Autowired
    public CurrencyCacheDeserializer(DictionaryService<Currency> currencyService) {
        super(Currency.class, currencyService);
    }
}