package uz.ciasev.ubdd_service.mvd_core.api.mib.webhook.dto.adm;

import lombok.*;
import uz.ciasev.ubdd_service.dto.internal.request.protocol.*;
import uz.ciasev.ubdd_service.exception.*;

import javax.validation.*;
import javax.validation.constraints.*;

@Data
public class MibAdmCaseRequestDTO {

    @Valid
    @NotNull(message = ErrorCode.PROTOCOL_REQUIRED)
    private ProtocolRequestDTO protocol;

    @Valid
    @NotNull(message = ErrorCode.INSPECTOR_REQUIRED)
    private ExternalInspectorRequestDTO inspector;

    @Valid
    @NotNull(message = ErrorCode.RESOLUTION_REQUIRED)
    private MibResolutionRequestDTO resolution;
}
