package uz.ciasev.ubdd_service.dto.internal.request.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.protocol.ProtocolDates;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolEditRequestDTO implements ProtocolDates {

    @NotNull(message = ErrorCode.REGISTRATION_TIME_REQUIRED)
    private LocalDateTime registrationTime;

    @NotNull(message = ErrorCode.VIOLATION_TIME_REQUIRED)
    private LocalDateTime violationTime;
}
