package uz.ciasev.ubdd_service.service.dict.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.person.Nationality;
import uz.ciasev.ubdd_service.repository.dict.person.NationalityRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class NationalityDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<Nationality>
        implements NationalityDictionaryService {

    private final String subPath = "nationalities";

    private final NationalityRepository repository;
    private final Class<Nationality> entityClass = Nationality.class;

    private final Long unknownId = 999L;


}
