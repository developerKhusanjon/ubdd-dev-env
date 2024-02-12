package uz.ciasev.ubdd_service.dto.internal.response.user;

import lombok.Getter;
import uz.ciasev.ubdd_service.dto.internal.dict.response.DictResponseDTO;
import uz.ciasev.ubdd_service.entity.dict.user.UserStatus;

@Getter
public class UserStatusResponseDTO extends DictResponseDTO {
    private final String color;
    private final String alias;
    private final Boolean isUserActive;

    public UserStatusResponseDTO(UserStatus entity) {
        super(entity);
        this.color = entity.getColor();
        this.isUserActive = entity.getIsUserActive();
        this.alias = "other";
    }
}
