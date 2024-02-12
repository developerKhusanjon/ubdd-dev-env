package uz.ciasev.ubdd_service.config.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsernameLoginDTO {

    private String username;
    private String password;
}
