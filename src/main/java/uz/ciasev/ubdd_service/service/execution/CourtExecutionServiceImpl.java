package uz.ciasev.ubdd_service.service.execution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.resolution.decision.Decision;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionDTO;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.exception.IncorrectExecutionTypeForPunishmentException;
import uz.ciasev.ubdd_service.service.resolution.punishment.PunishmentActionService;

import java.time.LocalDate;

import static uz.ciasev.ubdd_service.entity.dict.resolution.PunishmentTypeAlias.LICENSE_REVOCATION;

@RequiredArgsConstructor
@Service
@Slf4j
public class CourtExecutionServiceImpl implements CourtExecutionService {

    private final PunishmentActionService punishmentService;
    private final ExecutionCallbackService executionCallbackService;

    @Override
    public void executionLicenseRevocation(Decision decision, LocalDate executionDate) {
        Punishment licensePunishment = decision.getPunishments()
                .stream()
                .filter(p -> p.getType().is(LICENSE_REVOCATION))
                .findFirst()
                .orElseThrow(IncorrectExecutionTypeForPunishmentException::new);

        punishmentService.addExecution(licensePunishment, ExecutorType.COURT_EXECUTION_BY_MATERIAL, "Суд(Материал)", new ForceExecutionDTO(executionDate, ForceExecutionType.COURT_MATERIAL));
        executionCallbackService.executeCallback(decision);
    }
}
