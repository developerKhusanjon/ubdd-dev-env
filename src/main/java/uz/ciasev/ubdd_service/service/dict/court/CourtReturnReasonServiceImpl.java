package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.CourtReturnReason;
import uz.ciasev.ubdd_service.repository.dict.court.CourtReturnReasonRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryListService;

@Getter
@Service
@RequiredArgsConstructor
public class CourtReturnReasonServiceImpl extends AbstractDictionaryListService<CourtReturnReason> implements CourtReturnReasonService {

    private final String subPath = "court-return-reasons";

    private final Class<CourtReturnReason> entityClass = CourtReturnReason.class;
    private final CourtReturnReasonRepository repository;
}
