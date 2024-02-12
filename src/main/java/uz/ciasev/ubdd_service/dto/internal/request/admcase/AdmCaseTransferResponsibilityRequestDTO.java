package uz.ciasev.ubdd_service.dto.internal.request.admcase;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Data
public class AdmCaseTransferResponsibilityRequestDTO {

    @NotNull(message = ErrorCode.CONSIDERED_USER_REQUIRED)
    private Long considerUserId;
}
