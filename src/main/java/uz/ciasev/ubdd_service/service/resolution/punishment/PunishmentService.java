package uz.ciasev.ubdd_service.service.resolution.punishment;

import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecution;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;

import java.util.List;

public interface PunishmentService {

    Punishment create(Decision decision, Punishment punishment);

    void setDiscount(Punishment punishment, Invoice invoice);

    Punishment getById(Long id);

    List<ForceExecution> findForceExecutionByPunishmentId(Long id);

    Violator findViolatorByPunishmentId(Long id);

    Decision getDecisionByPunishmentId(Long id);

    Violator findViolatorByPenaltyId(Long id);

    Punishment findByPenaltyId(Long id);

    String buildExecutorNameForUser(User user);

    // not use outside the package
    Punishment save(Punishment punishment);

    boolean existsForceExecutionByPunishmentIdAndType(Long punishmentId, ForceExecutionType type);
}
