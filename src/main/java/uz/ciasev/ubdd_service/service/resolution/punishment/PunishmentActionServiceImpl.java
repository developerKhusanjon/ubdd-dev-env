package uz.ciasev.ubdd_service.service.resolution.punishment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecution;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionDTO;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.repository.resolution.execution.ForceExecutionRepository;
import uz.ciasev.ubdd_service.service.status.StatusService;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PunishmentActionServiceImpl implements PunishmentActionService {

    private final StatusService admStatusService;
    private final AdmEventService admEventService;
    private final ForceExecutionRepository forceExecutionRepository;
    private final PunishmentService punishmentService;


    @Override
    @Transactional
    public Punishment addExecution(User user, Punishment punishment, ExecutorType executorType) {

        punishment.setExecutionUserId(user.getId());
        String executorName = punishmentService.buildExecutorNameForUser(user);

        return addExecution(punishment, executorType, executorName, null);
    }

    @Transactional
    public Punishment addExecution(Punishment punishment, ExecutorType executorType, String executorName, @Nullable ForceExecutionDTO forceExecutionDTO) {
        return addExecution(punishment, executorType, List.of(executorName), forceExecutionDTO);
    }

    @Override
    @Transactional
    public Punishment addExecution(Punishment punishment, ExecutorType executorType, List<String> executorNames, @Nullable ForceExecutionDTO forceExecutionDTO) {

        executorNames.forEach(punishment::appendExecutionOrganName);

        if (forceExecutionDTO != null) {
            ForceExecution forceExecution = new ForceExecution(
                    punishment,
                    forceExecutionDTO.getExecutionDate(),
                    forceExecutionDTO.getType()
            );
            forceExecutionRepository.saveAndFlush(forceExecution);
        }

        return refreshStatusAndSave(punishment, executorType);
    }

    @Override
    public void deleteExecution(Punishment punishment, ExecutorType executorType, String executorName, ForceExecutionType forceExecutionType) {
        forceExecutionRepository.deleteAllByPunishmentAndType(punishment, forceExecutionType);
        admStatusService.cancelStatus(punishment, AdmStatusAlias.IN_EXECUTION_PROCESS, AdmStatusAlias.EXECUTED);

        punishment.removeExecutionOrganName(executorName);
        punishment.setExecutionDate(null);

        refreshStatusAndSave(punishment, executorType);
    }

    @Override
    public Punishment refreshStatusAndSave(Punishment punishment, ExecutorType executorType) {

        AdmStatusAlias ordinaryExecutionStatus = punishment.getDetail().calculateStatusAlias();
        admStatusService.addStatus(punishment, ordinaryExecutionStatus);

        Optional<ForceExecution> forceExecute = forceExecutionRepository.findFirstByPunishmentIdOrderByExecutionDateDesc(punishment.getId());
        if (forceExecute.isPresent()) {
            admStatusService.addStatus(punishment, AdmStatusAlias.EXECUTED);
        }

        punishment.setStatus(admStatusService.getStatus(punishment));

        if (punishment.getStatus().is(AdmStatusAlias.EXECUTED)) {
            LocalDate date = forceExecute.map(ForceExecution::getExecutionDate).orElseGet(() -> punishment.getDetail().getExecutionDate());
            punishment.setExecutionDate(date);
        }

        Punishment savedPunishment = punishmentService.save(punishment);


        admEventService.fireEvent(AdmEventType.PUNISHMENT_STATUS_CHANGE, Pair.of(savedPunishment, executorType));

        return savedPunishment;
    }
}
