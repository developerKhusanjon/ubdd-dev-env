package uz.ciasev.ubdd_service.service.resolution.compensation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecution;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionDTO;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.status.AdmStatusAlias;
import uz.ciasev.ubdd_service.event.AdmEventService;
import uz.ciasev.ubdd_service.event.AdmEventType;
import uz.ciasev.ubdd_service.repository.resolution.compensation.CompensationRepository;
import uz.ciasev.ubdd_service.repository.resolution.execution.ForceExecutionRepository;
import uz.ciasev.ubdd_service.service.status.StatusService;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompensationActionServiceImpl implements CompensationActionService {

    private final CompensationRepository compensationRepository;
    private final StatusService admStatusService;
    private final ForceExecutionRepository forceExecutionRepository;
    private final AdmEventService admEventService;

    @Override
    @Transactional
    public Compensation setExecution(Compensation compensation, List<String> executorNames, @Nullable ForceExecutionDTO forceExecutionDTO) {
        executorNames.forEach(compensation::appendExecutionOrganName);

        if (forceExecutionDTO != null) {
            ForceExecution forceExecution = new ForceExecution(
                    compensation,
                    forceExecutionDTO.getExecutionDate(),
                    forceExecutionDTO.getType()
            );
            forceExecutionRepository.saveAndFlush(forceExecution);
        }

        return refreshStatusAndSave(compensation);
    }

    @Override
    public void deleteExecution(Compensation compensation, String executorName, @Nullable ForceExecutionType forceExecutionType) {
        if (forceExecutionType != null) {
            forceExecutionRepository.deleteAllByCompensationAndType(compensation, forceExecutionType);
        }
        admStatusService.cancelStatus(compensation, AdmStatusAlias.IN_EXECUTION_PROCESS, AdmStatusAlias.EXECUTED);

        compensation.removeExecutionOrganName(executorName);
        compensation.setExecutionDate(null);

        refreshStatusAndSave(compensation);
    }

    @Override
    @Transactional
    public Compensation refreshStatusAndSave(Compensation compensation) {
        AdmStatusAlias ordinaryExecutionStatus = compensation.calculateStatusAlias();
        admStatusService.addStatus(compensation, ordinaryExecutionStatus);


        Optional<ForceExecution> forceExecute = forceExecutionRepository.findFirstByCompensationIdOrderByExecutionDateDesc(compensation.getId());
        if (forceExecute.isPresent()) {
            admStatusService.addStatus(compensation, AdmStatusAlias.EXECUTED);
        }


        compensation.setStatus(admStatusService.getStatus(compensation));
        if (compensation.getStatus().is(AdmStatusAlias.EXECUTED)) {
            LocalDate date = forceExecute.map(ForceExecution::getExecutionDate).orElseGet(compensation::getLastPayDate);
            compensation.setExecutionDate(date);
        }
        admEventService.fireEvent(AdmEventType.COMPENSATION_STATUS_CHANGE, compensation);

        return compensationRepository.saveAndFlush(compensation);
    }

}
