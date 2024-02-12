package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.CourtInstance;
import uz.ciasev.ubdd_service.repository.dict.court.CourtInstanceRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryListService;

@Getter
@Service
@RequiredArgsConstructor
public class CourtInstanceServiceImpl extends AbstractDictionaryListService<CourtInstance> implements CourtInstanceService {

    private final String subPath = "court-instances";

    private final Class<CourtInstance> entityClass = CourtInstance.class;
    private final CourtInstanceRepository repository;
}
