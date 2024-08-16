package uz.ciasev.ubdd_service.service.execution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionActionService;
import uz.ciasev.ubdd_service.service.resolution.decision.DecisionService;
import uz.ciasev.ubdd_service.service.resolution.ResolutionService;
import uz.ciasev.ubdd_service.service.status.AdmCaseStatusService;
import uz.ciasev.ubdd_service.service.status.StatusService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExecutionCallbackServiceImpl implements ExecutionCallbackService {

    private final DecisionService decisionService;
    private final DecisionActionService decisionActionService;
    private final DecisionRepository decisionRepository;
    private final AdmCaseStatusService caseStatusService;
    private final StatusService admStatusService;
    private final ResolutionService resolutionService;
    private final AdmCaseService admCaseService;

    @Override
    public void autoExecute(Resolution resolution) {
        try {
            List<Decision> decisions = decisionService.findByResolutionId(resolution.getId());
            boolean anyDecisionStatusChange = decisions.stream()
                    .map(this::handle)
                    .collect(Collectors.toSet())
                    .contains(true);
            if (anyDecisionStatusChange) {
                handle(resolution);
            }
        } catch (Exception e) {
            log.error("AutoExecute error", e);
        }
    }

    @Override
    public void executeCallback(Decision decision) {
        boolean isStatusChange = handle(decision);
        if (isStatusChange) {
//            handle(decision.getResolution());
            handle(resolutionService.getById(decision.getResolutionId()));
        }
    }

    @Override
    public void executeCallbackWithoutLazy(Decision decision) {
        handle(decision, false);
//        handle(decision.getResolution(), false);
        handle(resolutionService.getById(decision.getResolutionId()), false);

    }

    private boolean handle(Resolution resolution) {
        return handle(resolution, true);
    }

    private boolean handle(Decision decision, boolean lazy) {
        AdmStatusAlias status = calculateStatus(decisionRepository.getPartsStatusAliasesByDecision(decision));

        if (lazy && decision.getStatus().is(status)) {
            return false;
        }

        decisionActionService.saveStatus(decision, status);

        return true;
    }

    private boolean handle(Decision decision) {
        return handle(decision, true);
    }


    private boolean handle(Resolution resolution, boolean lazy) {
        AdmCase admCase = resolution.getAdmCase();

        AdmStatusAlias status = calculateStatus(decisionService.findStatusAliasesByResolutionId(resolution.getId()));
        LocalDate executedDate = null;

        if (lazy && resolution.getStatus().is(status)) {
            return false;
        }

        if (AdmStatusAlias.EXECUTED.equals(status)) {
            executedDate = LocalDate.now();
        }

        AdmStatus admStatus = admStatusService.addStatusAndGet(resolution, status);
        resolutionService.updateStatus(resolution, admStatus, executedDate);

        if (!resolution.isActive()) {
            return true;
        }

        caseStatusService.setStatus(admCase, status);
        admCaseService.update(admCase.getId(), admCase);
        return true;
    }

    private AdmStatusAlias calculateStatus(List<AdmStatusAlias> statuses) {
        if (statuses.stream().allMatch(AdmStatusAlias.EXECUTED::equals)) {
            return AdmStatusAlias.EXECUTED;
        } else if (statuses.stream().allMatch(AdmStatusAlias.DECISION_MADE::equals)) {
            return AdmStatusAlias.DECISION_MADE;
        } else {
            return AdmStatusAlias.IN_EXECUTION_PROCESS;
        }
    }
}
