package uz.ciasev.ubdd_service.dto.internal.trans.request.gcp;

import lombok.Data;
import uz.ciasev.ubdd_service.entity.dict.requests.trans.gcp.GcpTransEntityCreateDTOI;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Data
public class GcpTransEntityCreateRequestDTO implements GcpTransEntityCreateDTOI {

    @NotNull(message = ErrorCode.EXTERNAL_ID_REQUIRED)
    private Long externalId;
}
