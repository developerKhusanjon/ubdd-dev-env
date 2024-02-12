package uz.ciasev.ubdd_service.mvd_core.expapi.user.dto;

import lombok.Getter;
import lombok.Setter;
import uz.ciasev.ubdd_service.entity.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserLoginTimeRequest {

    private User user;
    private LocalDateTime createAt;


}
