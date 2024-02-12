package uz.ciasev.ubdd_service.dto.internal.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.utils.validator.UsernameStructure;
import uz.ciasev.ubdd_service.utils.validator.ValidPassword;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@UsernameStructure
public class UserCreateRequestDTO extends UserRequestDTO {

    @NotNull(message = ErrorCode.PINPP_REQUIRED)
    private String pinpp;

    @NotNull(message = ErrorCode.PASSWORD_REQUIRED)
    @ValidPassword
    private String password;

    @NotNull(message = ErrorCode.USERNAME_REQUIRED)
    private String username;

    @Override
    public User buildUser() {
        User user = super.buildUser();
        user.setPassword(this.password);
        user.setUsername(this.username);
        return user;
    }
}
