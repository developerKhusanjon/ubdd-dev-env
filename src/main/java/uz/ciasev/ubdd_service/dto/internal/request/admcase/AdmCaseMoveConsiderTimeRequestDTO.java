package uz.ciasev.ubdd_service.dto.internal.request.admcase;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AdmCaseMoveConsiderTimeRequestDTO {

    @NotNull(message = ErrorCode.CONSIDERED_TIME_REQUIRED)
    private LocalDateTime consideredTime;
}
