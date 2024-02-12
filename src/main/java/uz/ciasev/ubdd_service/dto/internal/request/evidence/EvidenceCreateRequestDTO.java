package uz.ciasev.ubdd_service.dto.internal.request.evidence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class EvidenceCreateRequestDTO extends EvidenceRequestDTO {

    @NotNull(message = ErrorCode.ADM_CASE_ID_REQUIRED)
    private Long admCaseId;
}
