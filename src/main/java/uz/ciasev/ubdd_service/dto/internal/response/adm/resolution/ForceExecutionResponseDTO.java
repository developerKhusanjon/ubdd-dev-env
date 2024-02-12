package uz.ciasev.ubdd_service.dto.internal.response.adm.resolution;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.resolution.execution.ForceExecution;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ForceExecutionResponseDTO {

    private final Long id;
    private final LocalDateTime createdTime;
    private final Long punishmentId;
    private final LocalDate executionDate;
    private final String type;

    public ForceExecutionResponseDTO(ForceExecution forceExecution) {
        this.id = forceExecution.getId();
        this.createdTime = forceExecution.getCreatedTime();
        this.punishmentId = forceExecution.getPunishmentId();
        this.executionDate = forceExecution.getExecutionDate();
        this.type = forceExecution.getType().name();
    }
}
