package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm;

import lombok.*;
import uz.ciasev.ubdd_service.exception.*;
import uz.ciasev.ubdd_service.utils.validator.*;

import javax.validation.constraints.*;
import java.time.*;

@Data
public class MibResolutionRequestDTO {

    @NotNull(message = ErrorCode.RESOLUTION_TIME_REQUIRED)
    private LocalDateTime resolutionTime;

    @NotNull(message = ErrorCode.RESOLUTION_NUMBER_REQUIRED)
    private String resolutionNumber;

    @NotNull
    @MoneyAmount(message = ErrorCode.PUNISHMENT_AMOUNT_INVALID)
    private Long penaltyAmount;

    @NotNull(message = ErrorCode.EXECUTION_FROM_DATE_REQUIRED)
    private LocalDate executionFromDate;

    private String inspectorSignature;
}
