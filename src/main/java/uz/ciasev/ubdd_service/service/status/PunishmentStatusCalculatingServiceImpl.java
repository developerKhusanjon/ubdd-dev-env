package uz.ciasev.ubdd_service.service.status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.service.resolution.evidence_decision.EvidenceDecisionCreateRequest;

@Service
@RequiredArgsConstructor
public class PunishmentStatusCalculatingServiceImpl implements PunishmentStatusCalculatingService {
    private final AdmStatusDictionaryService admStatusDictionaryService;

    @Override
    public AdmStatus getStartStatus(Punishment punishment) {
        AdmStatusAlias statusAlias = AdmStatusAlias.DECISION_MADE;

        if (punishment.getType().getAlias().equals(PunishmentTypeAlias.CONFISCATION))
            statusAlias = AdmStatusAlias.EXECUTED;

        if (punishment.getType().getAlias().equals(PunishmentTypeAlias.LICENSE_REVOCATION))
            statusAlias = AdmStatusAlias.IN_EXECUTION_PROCESS;

        return admStatusDictionaryService.findByAlias(statusAlias);
    }

    @Override
    public AdmStatus getStartStatus(EvidenceDecisionCreateRequest punishment) {
        return admStatusDictionaryService.findByAlias(AdmStatusAlias.DECISION_MADE);
    }

    @Override
    public AdmStatus getStartStatus(Compensation compensation) {
        return admStatusDictionaryService.findByAlias(AdmStatusAlias.DECISION_MADE);
    }
}
