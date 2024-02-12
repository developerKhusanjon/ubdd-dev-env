package uz.ciasev.ubdd_service.service.resolution.decision;

import lombok.RequiredArgsConstructor;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.action.DecisionStatusPermittedAction;
import uz.ciasev.ubdd_service.entity.admcase.AdmCase;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.*;
import uz.ciasev.ubdd_service.repository.resolution.decision.DecisionRepository;
import uz.ciasev.ubdd_service.service.admcase.AdmCaseAccessService;
import uz.ciasev.ubdd_service.service.status.AdmActionService;

import java.util.List;
import java.util.Optional;

//@Service
@RequiredArgsConstructor
public class DecisionAccessServiceImpl implements DecisionAccessService {

    protected final AdmActionService admActionService;
    protected final AdmCaseAccessService admCaseAccessService;
    protected final DecisionRepository decisionRepository;

    @Override
    public void checkSystemActionPermit(ActionAlias actionAlias, Decision decision) {

        if (!decision.getResolution().isActive()) {
            throw new ResolutionNotActiveException();
        }
        getPermissionOrThrow(actionAlias, decision);
    }

    @Override
    public void checkUserActionPermit(User user, ActionAlias actionAlias, Decision decision) {

        checkAccess(user, decision);

        checkUserAction(user, actionAlias, decision);
    }

    @Override
    public void checkAccess(User user, Decision decision) {
        checkAccessOnResolution(user, decision.getResolution());
    }

    @Override
    public void checkUserActionPermitNoOrgan(User user, ActionAlias actionAlias, Decision decision) {

        checkAccessOnResolutionNoOrgan(user, decision.getResolution());

        checkUserAction(user, actionAlias, decision);
    }

    private void checkUserAction(User user, ActionAlias actionAlias, Decision decision) {

        DecisionStatusPermittedAction permission = getPermissionOrThrow(actionAlias, decision);

        if (permission.getConsidererOnly()) {
            admCaseAccessService.checkConsiderOfAdmCase(user, decision.getResolution().getAdmCase());
        }
    }

    @Override
    public void checkIsNotCourt(Decision decision) {
        checkIsNotCourt(decision.getResolution());
    }

    @Override
    public void checkIsNotCourt(Resolution resolution) {
        if (resolution.getOrgan().isCourt()) {
            throw new CourtResolutionException();
        }
    }

    @Override
    public void checkIsNotCourtOrMaterial(Decision decision) {
        if (decision.getResolution().getOrgan().isCourt() && !is315(decision)) {
            throw new CourtResolutionException();
        }
    }

    @Override
    public boolean is315(Decision decision) {

        if (!decision.getIsCourt()) {
            return false;
        }

        return decisionRepository.resolutionExistsInMaterials(decision.getResolutionId()).orElse(false);
    }

    @Override
    public Optional<Decision> findOriginalDecisionFor315(Decision decision) {

        return decisionRepository.findOriginalDecisionFor315(decision.getResolutionId());
    }

//    @Override
//    public void checkPermitActionWithDecision(ActionAlias actionAlias, Decision decision) {
//        if (!admActionService.isDecisionActionPermitForStatus(actionAlias, decision.getStatus().getId()).isPresent())
//            throw new DecisionStatusNoSuitableException();
//    }
//
//    @Override
//    public void checkUserActionPermit(User user, ActionAlias actionAlias, Resolution resolution) {
//        checkAccessOnResolution(user, resolution);
//
//        DecisionStatusPermittedAction permission = getPermissionOrThrow(actionAlias, resolution.getStatus());
//
//        if (permission.getConsidererOnly()) {
//            admCaseAccessService.checkConsiderOfAdmCase(user, resolution.getAdmCase());
//        }
//    }

    private void checkAccessOnResolution(User user, Resolution resolution) {

        if (!resolution.isActive()) {
            throw new ResolutionNotActiveException();
        }
        admCaseAccessService.checkAccessOnAdmCase(user, resolution.getAdmCase());
    }

    private void checkAccessOnResolutionNoOrgan(User user, Resolution resolution) {

        if (!resolution.isActive()) {
            throw new ResolutionNotActiveException();
        }
        admCaseAccessService.checkAccessOnAdmCaseNoOrgan(user, resolution.getAdmCase());
    }

    private DecisionStatusPermittedAction getPermissionOrThrow(ActionAlias actionAlias, Decision decision) {
        AdmStatus admStatus = decision.getStatus();

        Optional<DecisionStatusPermittedAction> permission = admActionService.isDecisionActionPermitForStatus(actionAlias, admStatus.getId());

        if (permission.isEmpty()) {
            throw new DecisionStatusNoSuitableException(decision, actionAlias);
        }
        return permission.get();
    }

    @Override
    public List<ActionAlias> findDecisionPermittedActions(User user, Decision decision) {
        if (decision.getResolution().getOrgan().isCourt()) {
            return List.of();
        }

        AdmCase admCase = decision.getResolution().getAdmCase();

        if (admCaseAccessService.isHasNoAccessOnAdmCase(user, admCase)) {
            return List.of();
        }

        if (admCaseAccessService.isConsiderOfAdmCase(user, admCase)) {
            return admActionService.findDecisionPermittedActions(admCase.getStatusId());
        } else {
            return admActionService.findDecisionPermittedActionsNotConsiderer(admCase.getStatusId());
        }
    }
}