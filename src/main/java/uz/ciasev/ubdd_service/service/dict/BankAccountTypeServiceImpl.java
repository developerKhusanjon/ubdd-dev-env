package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.BankAccountType;
import uz.ciasev.ubdd_service.repository.dict.BankAccountTypeRepository;

@Service
@RequiredArgsConstructor
@Getter
public class BankAccountTypeServiceImpl extends SimpleEmiDictionaryServiceAbstract<BankAccountType>
        implements BankAccountTypeService {

    private final BankAccountTypeRepository repository;
    
    private final String subPath = "bank-account-types";
    private final Class<BankAccountType> entityClass = BankAccountType.class;
}
