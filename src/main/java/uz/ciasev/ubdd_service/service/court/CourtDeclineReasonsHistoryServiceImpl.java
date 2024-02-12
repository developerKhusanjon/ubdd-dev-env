package uz.ciasev.ubdd_service.service.court;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.second.CourtRegistrationStatusRequestDTO;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.court.CourtDeclineReasonsHistory;
import uz.ciasev.ubdd_service.repository.court.CourtDeclineReasonsHistoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourtDeclineReasonsHistoryServiceImpl implements CourtDeclineReasonsHistoryService {

    private final CourtDeclineReasonsHistoryRepository declineReasonsHistoryRepository;

    @Override
    public List<CourtDeclineReasonsHistory> findAllByCaseId(Long caseId) {
        return declineReasonsHistoryRepository.findAllByCaseIdOrderByCreatedTime(caseId);
    }

    @Override
    public CourtDeclineReasonsHistory save(AdmCase admCase, CourtRegistrationStatusRequestDTO registration) {
        CourtDeclineReasonsHistory declineReasonsHistory = new CourtDeclineReasonsHistory(admCase, registration.getDeclinedDate(), registration.getDeclinedReasons());
        return declineReasonsHistoryRepository.save(declineReasonsHistory);
    }
}
