package uz.ciasev.ubdd_service.utils.deserializer.dict;

import org.springframework.beans.factory.annotation.Autowired;
import uz.ciasev.ubdd_service.entity.dict.Bank;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;
import uz.ciasev.ubdd_service.utils.deserializer.AbstractDictDeserializer;

public class BankCacheDeserializer extends AbstractDictDeserializer<Bank> {

    @Autowired
    public BankCacheDeserializer(DictionaryService<Bank> bankService) {
        super(Bank.class, bankService);
    }
}