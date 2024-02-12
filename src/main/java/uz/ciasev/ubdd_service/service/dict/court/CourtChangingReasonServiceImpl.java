package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.CourtChangingReason;
import uz.ciasev.ubdd_service.repository.dict.court.CourtChangingReasonRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryListService;

@Getter
@Service
@RequiredArgsConstructor
public class CourtChangingReasonServiceImpl extends AbstractDictionaryListService<CourtChangingReason> implements CourtChangingReasonService {

    private final String subPath = "court-changing-reasons";

    private final Class<CourtChangingReason> entityClass = CourtChangingReason.class;
    private final CourtChangingReasonRepository repository;
}
