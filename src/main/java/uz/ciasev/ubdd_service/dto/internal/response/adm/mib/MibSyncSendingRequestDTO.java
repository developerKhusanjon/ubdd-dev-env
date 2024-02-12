package uz.ciasev.ubdd_service.dto.internal.response.adm.mib;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
public class MibSyncSendingRequestDTO {

    @NotNull(message = ErrorCode.DECISION_ID_REQUIRED)
    private Long decisionId;

    private Boolean checkByInvoice;

    private Boolean checkByCardNumber;

    public boolean getCheckByInvoice() {
        return Optional.ofNullable(checkByInvoice).orElse(false);
    }

    public boolean getCheckByCardNumber() {
        return Optional.ofNullable(checkByCardNumber).orElse(false);
    }

}
