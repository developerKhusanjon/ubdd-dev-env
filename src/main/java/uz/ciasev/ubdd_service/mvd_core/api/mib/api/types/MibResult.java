package uz.ciasev.ubdd_service.mvd_core.api.mib.api.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.mib.MibSendStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class MibResult {

    @NotNull(message = "requestId must not be null")
    private Long requestId;

    @NotNull(message = "admCaseId must not be null")
    private Long admCaseId;

    private Long decisionId;

    @NotNull(message = "sendTime must not be null")
    private LocalDateTime sendTime;

    @NotNull(message = "message must not be null")
    private String message;

}
