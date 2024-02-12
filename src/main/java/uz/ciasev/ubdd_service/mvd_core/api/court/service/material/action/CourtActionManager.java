package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.exception.ConflictedCourtActionError;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.mvd_core.api.court.dto.third.ksinn.ThirdCourtRequest;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatus;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourtActionManager {

    private final List<CourtActionBuilder> builders;
    private final CourtActionRoutChecker routChecker;

    public MaterialActionContext accept(MaterialBuilderContext buildContext, MaterialActionContext actionContext, ThirdCourtRequest request) {
        Collection<CourtAction> actions = build(buildContext, request);
        checkConflictedAction(actions);
        checkRoutAvailable(actionContext, request, actions);

        return apply(actions, actionContext);
    }

    private Collection<CourtAction> build(MaterialBuilderContext context, ThirdCourtRequest request) {
        Collection<CourtAction> actions = new ArrayList<>();

        for (CourtActionBuilder builder : builders) {
            builder.buildAction(context, request)
                    .ifPresent(actions::add);
        }

        return actions;
    }

    private void checkRoutAvailable(MaterialActionContext actionContext, ThirdCourtRequest request, Collection<CourtAction> actions) {
        CourtStatus currentStatus = actionContext.getCourtFields().getCourtStatus();
        CourtStatus newStatus = request.getStatus();

        routChecker.check(
                currentStatus.getAlias(),
                newStatus.getAlias(),
                actions.stream().map(CourtAction::getName).collect(Collectors.toList())
        );

    }

    private MaterialActionContext apply(Collection<CourtAction> actions, MaterialActionContext context) {

        List<CourtAction> sortedActions = actions.stream().sorted(Comparator.comparing(a -> a.getName().getAcceptOrder())).collect(Collectors.toList());

        for (CourtAction action : sortedActions) {
            context = action.apply(context);
        }

        return context;
    }

    private void checkConflictedAction(Collection<CourtAction> actions) {
        Set<CourtActionName> actionsName = actions.stream().map(CourtAction::getName).collect(Collectors.toSet());

        for (CourtAction action : actions) {
            var conflicts = action.getConflicts();
            conflicts.stream().filter(actionsName::contains).findFirst().ifPresent(conflictedAction -> {
                throw new ConflictedCourtActionError(action.getName(), conflictedAction);
            });
        }
    }

}
