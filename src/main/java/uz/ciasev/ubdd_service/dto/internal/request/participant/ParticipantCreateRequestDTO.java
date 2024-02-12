package uz.ciasev.ubdd_service.dto.internal.request.participant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class ParticipantCreateRequestDTO extends ParticipantProtocolRequestDTO {

    @NotNull(message = ErrorCode.PROTOCOL_ID_REQUIRED)
    private Long protocolId;

}
