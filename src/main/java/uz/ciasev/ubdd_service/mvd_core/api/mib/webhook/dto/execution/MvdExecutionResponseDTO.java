package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.execution;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MvdExecutionResponseDTO {

    @JsonProperty("amountPay")
    private Long finePayedAmount;

    public MvdExecutionResponseDTO(Long finePayedAmount) {
        this.finePayedAmount = finePayedAmount;
    }

    public MvdExecutionResponseDTO() {
        this.finePayedAmount = null;
    }


}
