package uz.ciasev.ubdd_service.service.dict.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipType;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipTypeAlias;
import uz.ciasev.ubdd_service.repository.dict.person.CitizenshipTypeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendDictionaryServiceAbstract;

@Service
@RequiredArgsConstructor
@Getter
public class CitizenshipTypeDictionaryServiceImpl extends SimpleBackendDictionaryServiceAbstract<CitizenshipType, CitizenshipTypeAlias>
        implements CitizenshipTypeDictionaryService {

    private final String subPath = "citizenship-types";

    private final CitizenshipTypeAlias unknownAlias = CitizenshipTypeAlias.UNKNOWN;

    private final CitizenshipTypeRepository repository;
    private final Class<CitizenshipType> entityClass = CitizenshipType.class;

    @Override
    public Class<CitizenshipTypeAlias> getAliasClass() {
        return CitizenshipTypeAlias.class;
    }
}
