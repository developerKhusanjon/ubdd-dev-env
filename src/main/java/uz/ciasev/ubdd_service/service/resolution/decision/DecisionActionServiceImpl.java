package uz.ciasev.ubdd_service.service.resolution.decision;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.service.status.StatusService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DecisionActionServiceImpl implements DecisionActionService {

    private final DecisionRepository decisionRepository;
    private final StatusService statusService;
    private final AdmEventService admEventService;

    @Override
    @Transactional
    public void calculateAndSetExecutedDate(Long decisionId) {
        Optional<Date> executionDateByPart = decisionRepository.extractExecutionDateFromPartsByDecision(decisionId);
        LocalDate executionDate = executionDateByPart.map(Date::toLocalDate).orElseGet(() -> decisionRepository.getOne(decisionId).getExecutionFromDate());
        decisionRepository.setExecutedDate(decisionId, executionDate);
    }

    @Override
    @Transactional
    public void saveStatus(Decision decision, AdmStatusAlias admStatusAlias) {
//        statusService.setStatus(decision, admStatusAlias);
        statusService.addStatus(decision, admStatusAlias);
        decision.setStatus(statusService.getStatus(decision));
        saveStatus(decision);
        admEventService.fireEvent(AdmEventType.DECISION_STATUS_CHANGE, decision);
    }

   @Override
    public void setMibPreSendHandled(Decision decision) {
        decisionRepository.setHandleByMibPreSendToTrue(decision.getId());
        decision.setHandleByMibPreSend(true);
    }

    private void saveStatus(Decision decision) {
        decisionRepository.saveStatus(decision.getId(), decision.getStatus().getId());
    }

}
