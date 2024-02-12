package uz.ciasev.ubdd_service.dto.internal.request.execution;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class InvoiceCloseRequestDTO {

    @NotNull(message = ErrorCode.CLOSE_INVOICE_REASON_REQUIRED)
    @Size(min = 1, max = 500, message = ErrorCode.CLOSE_INVOICE_REASON_MIN_MAX_LENGTH)
    private String reason;
}
