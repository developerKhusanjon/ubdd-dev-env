package uz.ciasev.ubdd_service.service.status;

import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.action.AdmStatusPermittedAction;
import uz.ciasev.ubdd_service.entity.action.DecisionStatusPermittedAction;

import java.util.List;
import java.util.Optional;

public interface AdmActionService {

    Optional<AdmStatusPermittedAction> getActionPermitForStatus(ActionAlias actionAlias, Long admStatusId);

    boolean isCaseActionPermitForStatus(ActionAlias actionAlias, Long admStatusId);

    Optional<DecisionStatusPermittedAction> isDecisionActionPermitForStatus(ActionAlias actionAlias, Long admStatusId);

    List<ActionAlias> findPermittedActionForStatus(Long admStatusId);

    List<ActionAlias> findNotConsiderActionForStatus(Long admStatusId);

    List<ActionAlias> findDecisionPermittedActions(Long admStatusId);

    List<ActionAlias> findDecisionPermittedActionsNotConsiderer(Long admStatusId);
}
