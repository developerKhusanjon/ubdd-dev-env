package uz.ciasev.ubdd_service.dto.internal.request.resolution;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.NotInFuture;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ManualPaymentRequestDTO {

    @NotNull(message = ErrorCode.PAID_AMOUNT_REQUIRED)
    private Long paidAmount;

    @NotInFuture(message = ErrorCode.LAST_PAY_DATE_IN_FUTURE)
    @NotNull(message = ErrorCode.LAST_PAY_DATE_REQUIRED)
    private LocalDate lastPayDate;
}
