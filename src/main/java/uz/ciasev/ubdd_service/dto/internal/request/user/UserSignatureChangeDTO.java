package uz.ciasev.ubdd_service.dto.internal.request.user;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserSignatureChangeDTO {

    @NotNull(message = ErrorCode.REASON_REQUIRED)
    @NotBlank
    @Size(max = 256, message = ErrorCode.REASON_LENGTH)
    private String reason;
}
