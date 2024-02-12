package uz.ciasev.ubdd_service.dto.internal.request.resolution.organ;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uz.ciasev.ubdd_service.mvd_core.api.court.types.CourtCompensationPayerType;
import uz.ciasev.ubdd_service.dto.internal.request.resolution.CompensationRequestDTO;
import uz.ciasev.ubdd_service.entity.dict.VictimType;
import uz.ciasev.ubdd_service.entity.resolution.compensation.Compensation;
import uz.ciasev.ubdd_service.entity.dict.BankAccountType;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ActiveOnly;
import uz.ciasev.ubdd_service.utils.validator.MoneyAmount;

import javax.validation.constraints.NotNull;

@Data
public class OrganCompensationRequestDTO implements CompensationRequestDTO {

    @JsonIgnore
    private Long violatorId;

    private Long victimId;

    @NotNull(message = ErrorCode.VICTIM_TYPE_REQUIRED)
    @ActiveOnly(message = ErrorCode.VICTIM_TYPE_DEACTIVATED)
    @JsonProperty(value = "victimTypeId")
    private VictimType victimType;

    @MoneyAmount(message = ErrorCode.COMPENSATION_AMOUNT_INVALID)
//    @Max(value = 25600000L, message = ErrorCode.COMPENSATION_AMOUNT_MORE_THEN_BRV)
    private Long amount;

    @JsonProperty(value = "bankAccountTypeId")
    private BankAccountType bankAccountType;

    public Compensation buildCompensation() {
        Compensation compensation = new Compensation();

        compensation.setAmount(this.amount);
        compensation.setVictimType(this.victimType);
        compensation.setPayerTypeId(CourtCompensationPayerType.VIOLATOR.getId());

        return compensation;
    }
}
