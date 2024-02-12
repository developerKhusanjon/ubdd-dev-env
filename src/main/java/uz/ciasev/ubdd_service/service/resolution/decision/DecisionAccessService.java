package uz.ciasev.ubdd_service.service.resolution.decision;

import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.Resolution;
import uz.ciasev.ubdd_service.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface DecisionAccessService {

    void checkSystemActionPermit(ActionAlias actionAlias, Decision decision);

    void checkUserActionPermit(User user, ActionAlias actionAlias, Decision decision);

    void checkUserActionPermitNoOrgan(User user, ActionAlias actionAlias, Decision decision);

    void checkAccess(User user, Decision decision);

    void checkIsNotCourt(Decision decision);

    void checkIsNotCourt(Resolution resolution);

    void checkIsNotCourtOrMaterial(Decision decision);

    boolean is315(Decision decision);

    Optional<Decision> findOriginalDecisionFor315(Decision decision);

    List<ActionAlias> findDecisionPermittedActions(User user, Decision decision);
}
