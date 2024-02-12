package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolTrackNumberRequestDTO {

    @NotNull(message = ErrorCode.TRACK_NUMBER_REQUIRED)
    @Size(max = 64, message = ErrorCode.TRACK_NUMBER_MIN_MAX_SIZE)
    @NotBlank(message = ErrorCode.TRACK_NUMBER_REQUIRED)
    private String trackNumber;

//    @NotNull(message = ErrorCode.PROTOCOL_LIST_REQUIRED)
//    @Size(min = 1, max = 1000, message = ErrorCode.PROTOCOL_LIST_MIN_MAX_SIZE)
//    private List<Long> protocolsId;
}
