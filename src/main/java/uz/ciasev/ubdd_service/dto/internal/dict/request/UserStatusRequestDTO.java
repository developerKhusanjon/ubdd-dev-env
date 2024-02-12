package uz.ciasev.ubdd_service.dto.internal.dict.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uz.ciasev.ubdd_service.entity.dict.requests.UserStatusDTOI;
import uz.ciasev.ubdd_service.entity.dict.user.UserStatus;
import uz.ciasev.ubdd_service.exception.ErrorCode;
import uz.ciasev.ubdd_service.service.dict.DictCreateRequest;
import uz.ciasev.ubdd_service.service.dict.DictUpdateRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserStatusRequestDTO extends BaseDictRequestDTO implements UserStatusDTOI, DictCreateRequest<UserStatus>, DictUpdateRequest<UserStatus> {
    @NotNull(message = ErrorCode.IS_USER_ACTIVE_REQUIRED)
    private Boolean isUserActive;

    @NotNull(message = ErrorCode.COLOR_REQUIRED)
    @Size(min= 1, max = 20, message = ErrorCode.COLOR_MIN_MAX_LENGTH)
    private String color;

    @Override
    public void applyToNew(UserStatus entity) {
        entity.construct(this);
    }

    @Override
    public void applyToOld(UserStatus entity) {
        entity.update(this);
    }
}
