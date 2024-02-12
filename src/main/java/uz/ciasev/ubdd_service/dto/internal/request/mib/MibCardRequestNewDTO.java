package uz.ciasev.ubdd_service.dto.internal.request.mib;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class MibCardRequestNewDTO extends MibCardRequestDTO {

    @NotNull(message = ErrorCode.DECISION_ID_REQUIRED)
    private Long decisionId;

    @NotNull(message = ErrorCode.MIB_OWNER_TYPE_REQUIRED)
//    @ActiveOnly(message = ErrorCode.MIB_OWNER_TYPE_DEACTIVATED)
    @JsonProperty(value = "ownerTypeId")
    private Long ownerTypeId;
}
