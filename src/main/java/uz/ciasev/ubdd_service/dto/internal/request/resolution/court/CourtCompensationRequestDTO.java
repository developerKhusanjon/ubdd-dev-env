package uz.ciasev.ubdd_service.dto.internal.request.resolution.court;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.CompensationRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.dict.evidence.Currency;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.MoneyAmount;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourtCompensationRequestDTO implements CompensationRequestDTO {

    @NotNull(message = ErrorCode.VIOLATOR_ID_REQUIRED)
    private Long violatorId;

    private Long payerTypeId;
    private String payerAdditionalInfo;
    private Long victimId;
    private Currency currency;

    @NotNull(message = ErrorCode.VICTIM_TYPE_REQUIRED)
    private VictimType victimType;

    @MoneyAmount(message = ErrorCode.COMPENSATION_AMOUNT_INVALID)
    private Long amount;

    public Compensation buildCompensation() {
        Compensation compensation = new Compensation();

        compensation.setAmount(this.amount);
        compensation.setVictimType(this.victimType);
        compensation.setPayerTypeId(this.payerTypeId);
        compensation.setPayerAdditionalInfo(this.payerAdditionalInfo);

        return compensation;
    }
}
