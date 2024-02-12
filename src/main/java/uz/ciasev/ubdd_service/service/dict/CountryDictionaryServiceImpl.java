package uz.ciasev.ubdd_service.service.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.Country;
import uz.ciasev.ubdd_service.repository.dict.CountryRepository;

@Getter
@Service
@RequiredArgsConstructor
public class CountryDictionaryServiceImpl extends SimpleEmiDictionaryServiceAbstract<Country> implements CountryDictionaryService {

    private final Long unknownId = 999L;

    private final String subPath = "countries";

    private final Class<Country> entityClass = Country.class;
    private final CountryRepository repository;
}
