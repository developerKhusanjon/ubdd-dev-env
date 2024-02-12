package uz.ciasev.ubdd_service.dto.internal.request.user;

import lombok.Data;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.ValidPassword;

import javax.validation.constraints.NotNull;

@Data
public class UserChangePasswordDTO {

    @NotNull(message = ErrorCode.PASSWORD_REQUIRED)
    @ValidPassword
    private String password;
}
