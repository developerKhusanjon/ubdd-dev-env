package uz.ciasev.ubdd_service.service.resolution.compensation;

import org.springframework.validation.annotation.Validated;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionDTO;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecutionType;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.List;

@Validated
public interface CompensationActionService {

    Compensation setExecution(Compensation compensation, List<String> executorNames, @Valid @Nullable ForceExecutionDTO forceExecutionDTO);

    void deleteExecution(Compensation compensation, String executorName, ForceExecutionType forceExecutionType);

    Compensation refreshStatusAndSave(Compensation compensation);

}
