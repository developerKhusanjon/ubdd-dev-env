package uz.ciasev.ubdd_service.service.resolution.punishment;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.entity.resolution.execution.ExecutorType;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionDTO;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;
import uz.ciasev.ubdd_service.entity.resolution.punishment.Punishment;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.utils.validator.Inspector;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.List;

@Validated
public interface PunishmentActionService {

    Punishment addExecution(@Inspector User user, Punishment punishment, ExecutorType changeReason);

    Punishment addExecution(Punishment punishment, ExecutorType changeReason, String executorName, @Valid @Nullable ForceExecutionDTO forceExecution);

    Punishment addExecution(Punishment punishment, ExecutorType changeReason, List<String> executorNames, @Valid @Nullable ForceExecutionDTO forceExecution);

    void deleteExecution(Punishment punishment, ExecutorType executorType, String executorName, ForceExecutionType forceExecutionType);

    Punishment refreshStatusAndSave(Punishment punishment, ExecutorType changeReason);

}
