package uz.ciasev.ubdd_service.service.dict.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.person.IntoxicationType;
import uz.ciasev.ubdd_service.repository.dict.person.IntoxicationTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class IntoxicationTypeDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<IntoxicationType>
        implements IntoxicationTypeDictionaryService {

    private final String subPath = "intoxication-types";

    private final IntoxicationTypeRepository repository;
    public Class<IntoxicationType> entityClass = IntoxicationType.class;
}
