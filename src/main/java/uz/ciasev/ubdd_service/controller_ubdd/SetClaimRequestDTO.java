package uz.ciasev.ubdd_service.controller_ubdd;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SetClaimRequestDTO {

    @NotNull
    private Long admCaseId;

    @NotNull
    private Long claimId;

}
