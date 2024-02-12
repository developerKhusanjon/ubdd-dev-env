package uz.ciasev.ubdd_service.repository.action;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.action.Action;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.entity.action.AdmStatusPermittedAction;
import uz.ciasev.ubdd_service.entity.action.DecisionStatusPermittedAction;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ActionRepository extends JpaRepository<Action, Long> {

    @Query("SELECT a.alias " +
            " FROM Action a " +
            "WHERE a.id IN (SELECT p.actionId FROM AdmStatusPermittedAction p WHERE p.statusId = :admStatusId)")
    List<ActionAlias> findAllAliasByAdmStatusId(@Param("admStatusId") Long admStatusId);

    @Query("SELECT p " +
            " FROM AdmStatusPermittedAction p " +
            "WHERE p.statusId = :admStatusId " +
            "  AND p.actionId = (SELECT a.id FROM Action a WHERE a.alias = :actionAlias)")
    Optional<AdmStatusPermittedAction> getPermittedCaseActionForStatus(@Param("admStatusId") Long admStatusId,
                                                                       @Param("actionAlias") ActionAlias actionAlias);

    @Query("SELECT COUNT(p.id) != 0 " +
            " FROM AdmStatusPermittedAction p " +
            "WHERE p.statusId = :admStatusId " +
            "  AND p.actionId = (SELECT a.id FROM Action a WHERE a.alias = :actionAlias)")
    boolean isPermittedCaseActionForStatus(@Param("admStatusId") Long admStatusId,
                                           @Param("actionAlias") ActionAlias actionAlias);

    @Query("SELECT p " +
            " FROM DecisionStatusPermittedAction p " +
            "WHERE p.statusId = :admStatusId " +
            "  AND p.actionId = (SELECT a.id FROM Action a WHERE a.alias = :actionAlias)")
    Optional<DecisionStatusPermittedAction> isPermittedDecisionActionForStatus(@Param("admStatusId") Long admStatusId,
                                                                               @Param("actionAlias") ActionAlias actionAlias);

    @Query("SELECT p.action.alias " +
            " FROM DecisionStatusPermittedAction p " +
            "WHERE p.statusId = :admStatusId")
    List<ActionAlias> decisionPermittedActions(@Param("admStatusId") Long admStatusId);

    @Query("SELECT p.action.alias " +
            " FROM DecisionStatusPermittedAction p " +
            "WHERE p.statusId = :admStatusId " +
            " AND p.considererOnly = false")
    List<ActionAlias> decisionPermittedActionsNotConsiderer(@Param("admStatusId") Long admStatusId);

    @Transactional
    @Modifying
    @Query(value = "delete from {h-schema}action where alias not in :declaredAliases", nativeQuery = true)
    void deleteAllByAliasNotIn(@Param("declaredAliases") Set<String> declaredAliases);

    @Query("SELECT a.alias " +
            " FROM Action a " +
            "WHERE a.id IN (SELECT p.actionId FROM AdmStatusPermittedAction p WHERE p.statusId = :admStatusId AND p.considererOnly = FALSE)")
    List<ActionAlias> findNotConsiderAliasByAdmStatusId(Long admStatusId);
}
