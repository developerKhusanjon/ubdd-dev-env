package uz.ciasev.ubdd_service.service.manual_fix;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SetClaimRequestDTO {

    @NotNull
    private Long admCaseId;

    @NotNull
    private Long claimId;

}
