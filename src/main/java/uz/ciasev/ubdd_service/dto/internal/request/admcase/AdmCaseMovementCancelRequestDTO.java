package uz.ciasev.ubdd_service.dto.internal.request.admcase;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Data
public class AdmCaseMovementCancelRequestDTO {
    @NotNull(message = ErrorCode.ADM_CASE_ID_REQUIRED)
    private Long admCaseId;
}
