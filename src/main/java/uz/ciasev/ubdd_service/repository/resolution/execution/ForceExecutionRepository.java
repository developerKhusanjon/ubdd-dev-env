package uz.ciasev.ubdd_service.repository.resolution.execution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecution;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;

import java.util.List;
import java.util.Optional;


public interface ForceExecutionRepository extends JpaRepository<ForceExecution, Long> {

    boolean existsByPunishmentId(Long punishmentId);

    List<ForceExecution> findByPunishmentId(Long punishmentId);
    boolean existsByPunishmentIdAndType(Long punishmentId, ForceExecutionType type);

    @Modifying(flushAutomatically = true)
    void deleteAllByPunishmentAndType(Punishment punishment, ForceExecutionType type);

    @Modifying(flushAutomatically = true)
    void deleteAllByCompensationAndType(Compensation compensation, ForceExecutionType type);

    Optional<ForceExecution> findFirstByPunishmentIdOrderByExecutionDateDesc(Long punishmentId);

    Optional<ForceExecution> findFirstByCompensationIdOrderByExecutionDateDesc(Long compensationId);
}
