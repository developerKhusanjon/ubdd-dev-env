package uz.ciasev.ubdd_service.mvd_core.api.court.service.material.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.mvd_core.api.court.exception.CourtActionRoutError;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName;
import uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias;

import java.util.*;
import java.util.stream.Collectors;

import static uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtActionName.*;
import static uz.ciasev.ubdd_service.entity.dict.court.CourtStatusAlias.*;

@Component
public class CourtActionRoutChecker {

    Map<CourtStatusAlias, Map<CourtStatusAlias, Collection<ActionPresentCondition>>> allowedAction;

    {

        Map<CourtStatusAlias, Collection<ActionPresentCondition>> fromRegistered = Map.of(
                REGISTERED_IN_COURT, List.of(Required.of(UPDATE_CASE)),
                JUDGE_APPOINTED, List.of(Required.of(UPDATE_CASE)),
                PROCESS_REVIEW, List.of(Required.of(UPDATE_CASE)),
                PAUSED, List.of(Required.of(UPDATE_CASE)),
                MERGED, List.of(Required.of(UPDATE_CASE), Required.of(MERGE), Allowed.of(SEPARATION)),
                RESOLVED, List.of(Required.of(UPDATE_CASE), OneRequired.of(RESOLUTION, MOVEMENT), Allowed.of(SEPARATION)),
                RETURNED, List.of(Required.of(UPDATE_CASE), Required.of(RETURNING), Allowed.of(SEPARATION))
        );

        Map<CourtStatusAlias, Collection<ActionPresentCondition>> fromInProcessGroup = Map.of(
                PAUSED, List.of(Required.of(UPDATE_CASE)),
                PROCESS_REVIEW, List.of(Required.of(UPDATE_CASE)),
                JUDGE_APPOINTED, List.of(Required.of(UPDATE_CASE)),
                MERGED, List.of(Required.of(UPDATE_CASE), Required.of(MERGE), Allowed.of(SEPARATION)),
                RESOLVED, List.of(Required.of(UPDATE_CASE), OneRequired.of(RESOLUTION, MOVEMENT), Allowed.of(SEPARATION)),
                RETURNED, List.of(Required.of(UPDATE_CASE), Required.of(RETURNING), Allowed.of(SEPARATION))
        );


        Map<CourtStatusAlias, Collection<ActionPresentCondition>> fromReturningGroup = Map.of(
                PASSED_TO_ARCHIVE, List.of(Required.of(UPDATE_INFORMATION_STATUS)),
                PROCESS_REVIEW, List.of(Required.of(REVIEW), Required.of(UPDATE_CASE)),
                JUDGE_APPOINTED, List.of(Required.of(REVIEW), Required.of(UPDATE_CASE)),
                MERGED, List.of(Required.of(REVIEW), Required.of(UPDATE_CASE), Required.of(MERGE), Allowed.of(SEPARATION)),
                RESOLVED, List.of(OneRequired.of(REVIEW, EDITING_OF_RETURNING), Required.of(UPDATE_CASE), OneRequired.of(RESOLUTION, MOVEMENT), Allowed.of(SEPARATION)),
                RETURNED, List.of(OneRequired.of(REVIEW, EDITING_OF_RETURNING), Required.of(UPDATE_CASE), Required.of(RETURNING), Allowed.of(SEPARATION))
        );


        Map<CourtStatusAlias, Collection<ActionPresentCondition>> fromResolutionGroup = Map.of(
                PASSED_TO_ARCHIVE, List.of(Required.of(UPDATE_INFORMATION_STATUS)),
                PROCESS_REVIEW, List.of(Required.of(REVIEW), Required.of(UPDATE_CASE)),
                JUDGE_APPOINTED, List.of(Required.of(REVIEW), Required.of(UPDATE_CASE)),
                MERGED, List.of(Required.of(REVIEW), Required.of(UPDATE_CASE), Required.of(MERGE), Allowed.of(SEPARATION)),
                RESOLVED, List.of(OneRequired.of(REVIEW, EDITING_OF_RESOLUTION), Required.of(UPDATE_CASE), OneRequired.of(RESOLUTION, MOVEMENT), Allowed.of(SEPARATION)),
                RETURNED, List.of(OneRequired.of(REVIEW, EDITING_OF_RESOLUTION), Required.of(UPDATE_CASE), Required.of(RETURNING), Allowed.of(SEPARATION))
        );


        allowedAction = Map.of(
                REGISTERED_IN_COURT, fromRegistered,
                JUDGE_APPOINTED, fromInProcessGroup,
                PROCESS_REVIEW, fromInProcessGroup,
                PAUSED, fromInProcessGroup,
                RESOLVED, fromResolutionGroup,
                RETURNED, fromReturningGroup
//                PASSED_TO_ARCHIVE, formArchiveGroup // это не возможно, я больше ну буду сетить это статус в поля материала
        );

    }

    public void check(CourtStatusAlias from, CourtStatusAlias to, Collection<CourtActionName> actions) {

        Collection<ActionPresentCondition> routs = Optional.of(allowedAction)
                .map(m -> m.get(from))
                .map(m -> m.get(to))
//                .orElseThrow(() -> new CourtActionRoutError(from, to, "Способ перехода не определен"));
                .orElseThrow(() -> new CourtActionRoutError(from, to, "Transition way no defined"));

        checkDeclaration(from, to, actions, routs);
        checkPresent(from, to, actions, routs);
    }

    // Проверяет что бы содержались только задекларированые действия
    private void checkDeclaration(CourtStatusAlias from, CourtStatusAlias to, Collection<CourtActionName> presentActions, Collection<ActionPresentCondition> routs) {
        Set<CourtActionName> declaredAction = routs.stream().flatMap(r -> r.getActions().stream()).collect(Collectors.toSet());

        if (!declaredAction.containsAll(presentActions)) {
            String notDeclaredActionNames = presentActions.stream()
                    .filter(a -> !declaredAction.contains(a))
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            throw new CourtActionRoutError(from, to, "Do not know how handle action(actions) " + notDeclaredActionNames);
        }
    }

    // Проверяет что бы все задекларированые действия содержались
    private void checkPresent(CourtStatusAlias from, CourtStatusAlias to, Collection<CourtActionName> presentActions, Collection<ActionPresentCondition> conditions) {
        for (ActionPresentCondition presentCondition : conditions) {
            if (!presentCondition.test(presentActions)) {
                String missedAction = presentCondition.getActions().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "));

                throw new CourtActionRoutError(from, to, "One of required action missing: " + missedAction);
            }
        }
    }


    public interface ActionPresentCondition {

        Collection<CourtActionName> getActions();

        boolean test(Collection<CourtActionName> actions);

    }

    @Getter
    @RequiredArgsConstructor
    public static class Required implements ActionPresentCondition {

        private final CourtActionName action;

        public static Required of(CourtActionName action) {
            return new Required(action);
        }

        @Override
        public Collection<CourtActionName> getActions() {
            return List.of(action);
        }

        @Override
        public boolean test(Collection<CourtActionName> actions) {
            return actions.stream()
                    .anyMatch(action::equals);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class Allowed implements ActionPresentCondition {

        private final CourtActionName action;

        @Override
        public Collection<CourtActionName> getActions() {
            return List.of(action);
        }

        public static Allowed of(CourtActionName action) {
            return new Allowed(action);
        }

        @Override
        public boolean test(Collection<CourtActionName> actions) {
            return true;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class OneRequired implements ActionPresentCondition {

        private final CourtActionName firstAction;
        private final CourtActionName secondAction;

        @Override
        public Collection<CourtActionName> getActions() {
            return List.of(firstAction, secondAction);
        }

        public static OneRequired of(CourtActionName action1, CourtActionName action2) {
            return new OneRequired(action1, action2);
        }

        @Override
        public boolean test(Collection<CourtActionName> actions) {
            boolean firsActionPresent = actions.stream()
                    .anyMatch(firstAction::equals);

            boolean secondActionPresent = actions.stream()
                    .anyMatch(secondAction::equals);

            return Boolean.logicalXor(firsActionPresent, secondActionPresent);
        }
    }

//    @Getter
//    @RequiredArgsConstructor
//    public static class OneRequired implements ActionPresentCondition {
//
//        private final List<CourtActionName> requiredAction;
//
//        @Override
//        public Collection<CourtActionName> getActions() {
//            return requiredAction;
//        }
//
//        public static OneRequired of(CourtActionName... actions) {
//            return new OneRequired(List.of(actions));
//        }
//
//        @Override
//        public boolean test(Collection<CourtActionName> testedActions) {
//            Map<Boolean, Long> res = requiredAction.stream()
//                    .collect(Collectors.groupingBy(
//                            a -> testedActions.stream().anyMatch(a::equals),
//                            Collectors.counting()
//                    ));
//
//
//            return res.getOrDefault(true, 0L) == 1;
//        }
//    }

}
