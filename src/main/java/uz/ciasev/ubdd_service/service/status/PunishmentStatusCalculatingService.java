package uz.ciasev.ubdd_service.service.status;

import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.service.resolution.evidence_decision.EvidenceDecisionCreateRequest;

public interface PunishmentStatusCalculatingService {

    AdmStatus getStartStatus(Punishment punishment);

    AdmStatus getStartStatus(EvidenceDecisionCreateRequest punishment);

    AdmStatus getStartStatus(Compensation compensation);
}
