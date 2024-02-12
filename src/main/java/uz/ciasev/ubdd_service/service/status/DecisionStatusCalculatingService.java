package uz.ciasev.ubdd_service.service.status;

import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.service.resolution.ResolutionCreateRequest;

public interface DecisionStatusCalculatingService {

    AdmStatus getStartStatus(Decision decision);

    AdmStatus getStartStatus(ResolutionCreateRequest decision);
}
