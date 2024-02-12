package uz.ciasev.ubdd_service.service.resolution.punishment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.invoice.Invoice;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecution;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.PenaltyPunishment;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.status.AdmStatus;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.entity.violator.Violator;
import uz.ciasev.ubdd_service.exception.notfound.EntityByIdNotFound;
import uz.ciasev.ubdd_service.exception.notfound.EntityByParamsNotFound;
import uz.ciasev.ubdd_service.repository.resolution.execution.ForceExecutionRepository;
import uz.ciasev.ubdd_service.repository.resolution.punishment.PunishmentRepository;
import uz.ciasev.ubdd_service.service.status.StatusService;
import uz.ciasev.ubdd_service.utils.FormatUtils;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PunishmentServiceImpl implements PunishmentService {

    private final PunishmentRepository punishmentRepository;
    private final StatusService admStatusService;
    private final ForceExecutionRepository forceExecutionRepository;

    @Override
    public Punishment create(Decision decision, Punishment punishment) {

        punishment.setDecision(decision);
        AdmStatus admStatus = admStatusService.getStatusOrDefault(punishment, punishment.getDetail().calculateStatusAlias());
        punishment.setStatus(admStatus);
        return save(punishment);
    }

    @Override
    public Punishment getById(Long id) {
        return punishmentRepository
                .findById(id)
                .orElseThrow(() -> new EntityByIdNotFound(Punishment.class, id));
    }

    @Override
    public List<ForceExecution> findForceExecutionByPunishmentId(Long id) {

        return forceExecutionRepository
                .findByPunishmentId(id);
    }

    @Override
    public boolean existsForceExecutionByPunishmentIdAndType(Long punishmentId, ForceExecutionType type) {
        return forceExecutionRepository
                .existsByPunishmentIdAndType(punishmentId, type);
    }

    @Override
    public Violator findViolatorByPunishmentId(Long id) {

        return punishmentRepository
                .findViolatorByPunishmentId(id)
                .orElseThrow(() -> new EntityByIdNotFound(Punishment.class, id));
    }

    @Override
    public Decision getDecisionByPunishmentId(Long id) {

        return punishmentRepository
                .findDecisionByPunishmentId(id)
                .orElseThrow(() -> new EntityByIdNotFound(Punishment.class, id));
    }

    @Override
    public Violator findViolatorByPenaltyId(Long id) {

        return punishmentRepository
                .findViolatorByPenaltyId(id)
                .orElseThrow(() -> new EntityByIdNotFound(PenaltyPunishment.class, id));
    }

    @Override
    public Punishment findByPenaltyId(Long id) {

        return punishmentRepository.findByPenaltyId(id)
                .orElseThrow(() -> new EntityByParamsNotFound(Punishment.class, "penaltyId", id));
    }

    @Override
    public void setDiscount(Punishment punishment, Invoice invoice) {
        var penalty = punishment.getPenalty();

        if (penalty.getIsDiscount70()) {
            penalty.setDiscount70Amount(invoice.getDiscount70Amount());
            penalty.setDiscount70ForDate(invoice.getDiscount70ForDate());
        }

        if (penalty.getIsDiscount50()) {
            penalty.setDiscount50Amount(invoice.getDiscount50Amount());
            penalty.setDiscount50ForDate(invoice.getDiscount50ForDate());
        }

        save(punishment);
    }

    @Override
    public String buildExecutorNameForUser(User user) {

        StringBuilder builder = new StringBuilder(user.getFio());

        if (user.getOrgan() != null) {
            builder.append("(")
                    .append(user.getOrgan().getDefaultName())
                    .append(")");
        }

        return builder.toString();
    }

    @Override
    public Punishment save(Punishment punishment) {
        punishment.setAmountText(FormatUtils.punishmentToString(punishment));
        return punishmentRepository.saveAndFlush(punishment);
    }
}
