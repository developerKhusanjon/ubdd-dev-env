package uz.ciasev.ubdd_service.dto.internal.request.resolution;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class PunishmentExecuteWithoutBillingDTO extends ManualPaymentRequestDTO {

    @JsonAlias("discountAmount")
    private Long discount70Amount;

    @JsonAlias("discountForDate")
    private LocalDate discount70ForDate;

    private Long discount50Amount;

    private LocalDate discount50ForDate;
}
