package uz.ciasev.ubdd_service.service.dict.person;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipCalculatingMode;
import uz.ciasev.ubdd_service.entity.dict.person.CitizenshipCalculatingModeAlias;
import uz.ciasev.ubdd_service.repository.dict.person.CitizenshipCalculatingModeRepository;
import uz.ciasev.ubdd_service.service.dict.SimpleBackendDictionaryServiceAbstract;

@Getter
@Service
@RequiredArgsConstructor
public class CitizenshipCalculatingModeDictionaryServiceImpl extends SimpleBackendDictionaryServiceAbstract<CitizenshipCalculatingMode, CitizenshipCalculatingModeAlias> implements CitizenshipCalculatingModeDictionaryService {

    private final String subPath = "citizenship-calculating-modes";

    private final Class<CitizenshipCalculatingMode> entityClass = CitizenshipCalculatingMode.class;
    private final CitizenshipCalculatingModeRepository repository;

    @Override
    public Class<CitizenshipCalculatingModeAlias> getAliasClass() {
        return CitizenshipCalculatingModeAlias.class;
    }
}
