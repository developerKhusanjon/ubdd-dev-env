package uz.ciasev.ubdd_service.service.dict;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.request.BankRequestDTO;
import uz.ciasev.ubdd_service.dto.internal.response.dict.BankResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.Bank;
import uz.ciasev.ubdd_service.repository.dict.BankRepository;

@Service
@RequiredArgsConstructor
@Getter
public class BankDictionaryServiceImpl extends AbstractDictionaryCRUDService<Bank, BankRequestDTO, BankRequestDTO> implements BankDictionaryService {

    private final String subPath = "banks";
    private final TypeReference<BankRequestDTO> createRequestDTOClass = new TypeReference<>(){};
    private final TypeReference<BankRequestDTO> updateRequestDTOClass = new TypeReference<>(){};

    private final Class<Bank> entityClass = Bank.class;
    private final BankRepository repository;

    @Override
    public BankResponseDTO buildResponseDTO(Bank entity) {
        return new BankResponseDTO(entity);
    }

    @Override
    public BankResponseDTO buildListResponseDTO(Bank entity) {return new BankResponseDTO(entity);}
}
