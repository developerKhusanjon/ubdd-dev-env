package uz.ciasev.ubdd_service.entity.resolution.execution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForceExecutionDTO {

    @NotNull
    private LocalDate executionDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    protected ForceExecutionType type;
}
