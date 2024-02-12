package uz.ciasev.ubdd_service.dto.internal.request.settings;

import lombok.Getter;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.MoneyAmount;

@Getter
public class BrvUpdateRequestDTO {

    @MoneyAmount(message = ErrorCode.BRV_AMOUNT_INVALID)
    private Long amount;
}
