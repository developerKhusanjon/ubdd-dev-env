package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.dict.response.AliasedStatusDictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;
import uz.ciasev.ubdd_service.repository.dict.court.CourtStatusRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractAliasedDictionaryListService;

@Getter
@Service
@RequiredArgsConstructor
public class CourtStatusDictionaryServiceImpl extends AbstractAliasedDictionaryListService<CourtStatus, CourtStatusAlias> implements CourtStatusDictionaryService {

    private final String subPath = "court-statuses";

    private final Class<CourtStatus> entityClass = CourtStatus.class;
    private final CourtStatusRepository repository;

    @Override
    public Class<CourtStatusAlias> getAliasClass() {
        return CourtStatusAlias.class;
    }

    @Override
    public Object buildListResponseDTO(CourtStatus entity) {
        return new AliasedStatusDictResponseDTO(entity);
    }
}
