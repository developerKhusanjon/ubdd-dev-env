package uz.ciasev.ubdd_service.mvd_core.expapi.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginTimeResponse {

    private Long id;
    private String username;
    private String address;
    private LocalDateTime createAt;

//    public UserLoginTimeResponse(UserLoginTime entitiy){
//        this.id = entitiy.getId();
//        this.username = entitiy.getUser().getUsername();
//        this.address = entitiy.getUser().getPerson().getBirthAddress().getFullAddressText();
//        this.createAt = entitiy.getCreateAt();
//    }

}
