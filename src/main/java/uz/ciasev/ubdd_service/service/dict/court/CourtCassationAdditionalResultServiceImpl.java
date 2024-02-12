package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.CourtCassationAdditionalResult;
import uz.ciasev.ubdd_service.repository.dict.court.CourtCassationAdditionalResultRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryListService;

@Getter
@Service
@RequiredArgsConstructor
public class CourtCassationAdditionalResultServiceImpl extends AbstractDictionaryListService<CourtCassationAdditionalResult> implements CourtCassationAdditionalResultService {

    private final String subPath = "court-cassation-additional-results";

    private final Class<CourtCassationAdditionalResult> entityClass = CourtCassationAdditionalResult.class;
    private final CourtCassationAdditionalResultRepository repository;
}
