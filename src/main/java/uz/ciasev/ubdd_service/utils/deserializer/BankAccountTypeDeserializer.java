package uz.ciasev.ubdd_service.utils.deserializer;

import uz.ciasev.ubdd_service.entity.dict.BankAccountType;
import uz.ciasev.ubdd_service.service.dict.DictionaryService;

public class BankAccountTypeDeserializer extends AbstractEntityDeserializer<BankAccountType> {

    public BankAccountTypeDeserializer(DictionaryService<BankAccountType> service) {
        super(BankAccountType.class, service::getById);
    }
}
