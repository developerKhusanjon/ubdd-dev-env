package uz.ciasev.ubdd_service.dto.internal.request.settings;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidFutureDate;
import uz.ciasev.ubdd_service.utils.validator.ValidPastDate;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode(callSuper = true)
public class BrvCreateRequestDTO extends BrvUpdateRequestDTO {

    @NotNull(message = ErrorCode.BRV_FROM_DATE_REQUIRED)
    @ValidFutureDate(plusDays = 30, message = ErrorCode.BRV_FROM_DATE_CANNOT_BE_MORE_THAN_ONE_MONTH_IN_FUTURE)
    @ValidPastDate(minusDays = 10, message = ErrorCode.BRV_FROM_DATE_CANNOT_BE_OLDER_THAN_TEN_DAYS)
    private LocalDate fromDate;
}
