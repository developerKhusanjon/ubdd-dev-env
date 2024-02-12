package uz.ciasev.ubdd_service.service.status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;

@Service
@RequiredArgsConstructor
public class DecisionStatusCalculatingServiceImpl implements DecisionStatusCalculatingService {
    private final AdmStatusDictionaryService admStatusDictionaryService;

    @Override
    public AdmStatus getStartStatus(Decision decision) {
        return admStatusDictionaryService.findByAlias(AdmStatusAlias.DECISION_MADE);
    }

    @Override
    public AdmStatus getStartStatus(ResolutionCreateRequest decision) {
        return admStatusDictionaryService.findByAlias(AdmStatusAlias.DECISION_MADE);
    }
}
