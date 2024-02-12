package uz.ciasev.ubdd_service.service.dict.court;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.court.CourtCancelingReason;
import uz.ciasev.ubdd_service.repository.dict.court.CourtCancelingReasonRepository;
import uz.ciasev.ubdd_service.service.dict.AbstractDictionaryListService;

@Getter
@Service
@RequiredArgsConstructor
public class CourtCancelingReasonServiceImpl extends AbstractDictionaryListService<CourtCancelingReason> implements CourtCancelingReasonService {

    private final String subPath = "court-canceling-reasons";

    private final Class<CourtCancelingReason> entityClass = CourtCancelingReason.class;
    private final CourtCancelingReasonRepository repository;
}
