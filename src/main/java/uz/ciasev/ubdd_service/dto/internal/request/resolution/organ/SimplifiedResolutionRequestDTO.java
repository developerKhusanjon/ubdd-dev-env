package uz.ciasev.ubdd_service.dto.internal.request.resolution.organ;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.MoneyAmount;

import javax.validation.constraints.NotNull;

@Data
public class SimplifiedResolutionRequestDTO {

    @NotNull(message = ErrorCode.ADM_CASE_ID_REQUIRED)
    private Long admCaseId;

    @MoneyAmount(message = ErrorCode.PUNISHMENT_AMOUNT_INVALID)
    private Long amountPenalty;

    private String signature;
}
