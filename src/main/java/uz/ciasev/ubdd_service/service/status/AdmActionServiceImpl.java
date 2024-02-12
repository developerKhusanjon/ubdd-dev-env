package uz.ciasev.ubdd_service.service.status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.action.AdmStatusPermittedAction;
import uz.ciasev.ubdd_service.entity.action.DecisionStatusPermittedAction;
import uz.ciasev.ubdd_service.repository.action.ActionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdmActionServiceImpl implements AdmActionService {

    private final ActionRepository actionRepository;

    @Override
    public Optional<AdmStatusPermittedAction> getActionPermitForStatus(ActionAlias actionAlias, Long admStatusId) {
        return actionRepository.getPermittedCaseActionForStatus(admStatusId, actionAlias);
    }

    @Override
    public boolean isCaseActionPermitForStatus(ActionAlias actionAlias, Long admStatusId) {
        return actionRepository.isPermittedCaseActionForStatus(admStatusId, actionAlias);
    }

    @Override
    public Optional<DecisionStatusPermittedAction> isDecisionActionPermitForStatus(ActionAlias actionAlias, Long admStatusId) {
        return actionRepository.isPermittedDecisionActionForStatus(admStatusId, actionAlias);
    }

    @Override
    public List<ActionAlias> findPermittedActionForStatus(Long admStatusId) {
        return actionRepository.findAllAliasByAdmStatusId(admStatusId);
    }

    @Override
    public List<ActionAlias> findNotConsiderActionForStatus(Long admStatusId) {
        return actionRepository.findNotConsiderAliasByAdmStatusId(admStatusId);
    }

    @Override
    public List<ActionAlias> findDecisionPermittedActions(Long admStatusId) {
        return actionRepository.decisionPermittedActions(admStatusId);
    }

    @Override
    public List<ActionAlias> findDecisionPermittedActionsNotConsiderer(Long admStatusId) {
        return actionRepository.decisionPermittedActionsNotConsiderer(admStatusId);
    }
}
