package uz.ciasev.ubdd_service.config.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import uz.ciasev.ubdd_service.entity.user.User;

public interface UserJWTService {

    JWTToken generateToken(User user, String loginType);

    DecodedJWT decode(String token);

    User getUser(DecodedJWT token);
}
