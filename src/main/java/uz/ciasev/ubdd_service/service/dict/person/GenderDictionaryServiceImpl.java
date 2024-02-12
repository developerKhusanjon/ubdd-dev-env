package uz.ciasev.ubdd_service.service.dict.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.person.Gender;
import uz.ciasev.ubdd_service.repository.dict.person.GenderRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleEmiDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class GenderDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<Gender> implements GenderDictionaryService {

    private final Long unknownId = 999L;

    private final String subPath = "genders";

    private final Class<Gender> entityClass = Gender.class;
    private final GenderRepository repository;
}
