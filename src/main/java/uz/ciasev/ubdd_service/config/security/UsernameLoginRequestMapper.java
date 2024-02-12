package uz.ciasev.ubdd_service.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class UsernameLoginRequestMapper implements LoginRequestMapper<UsernameLoginDTO> {

    @Override
    public Authentication build(UsernameLoginDTO body) {
        return new UsernameAuthenticationToken(body.getUsername(), body.getPassword());
    }

    @Override
    public Class getBodyClass() {
        return UsernameLoginDTO.class;
    }

    @Override
    public boolean validateCredentials(UsernameLoginDTO body) {
        return body != null
                && body.getUsername() != null
                && body.getPassword() != null
                && !body.getUsername().isEmpty();
    }


    @Override
    public String loginType() {
        return "username";
    }

}
