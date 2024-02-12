package uz.ciasev.ubdd_service.dto.internal.request.victim;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class VictimCreateRequestCoreDTO extends VictimProtocolRequestCoreDTO {

    @NotNull(message = ErrorCode.PROTOCOL_ID_REQUIRED)
    private Long protocolId;
}
