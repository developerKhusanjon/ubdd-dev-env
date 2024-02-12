package uz.ciasev.ubdd_service.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.user.UserRepository;
import uz.ciasev.ubdd_service.service.user.UserPermissionService;
import uz.ciasev.ubdd_service.utils.DateTimeUtils;

import java.util.Date;
import java.util.List;

import static uz.ciasev.ubdd_service.config.security.SecurityConstants.JWT_ISSUER;

@Slf4j
@Service
public class UserJWTServiceImpl implements UserJWTService {

    private final String oauth2SymmetricSecret;
    private final Long tokenExpiredHours;
    private final UserRepository userRepository;
    private final UserPermissionService permissionService;

    @Autowired
    public UserJWTServiceImpl(@Value("${mvd-ciasev.jwt.token.symmetric-key}") String oauth2SymmetricSecret,
                              @Value("${mvd-ciasev.jwt.token.expiration-period-hours}") Long tokenExpiredHours,
                              UserRepository userRepository,
                              UserPermissionService permissionService) {
        this.oauth2SymmetricSecret = oauth2SymmetricSecret;
        this.tokenExpiredHours = tokenExpiredHours;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }

    @Override
    public JWTToken generateToken(User user, String loginType) {
        Date expiresAt = buildExpirationTime();

        String token = JWT
                .create()
                .withIssuer(JWT_ISSUER)
                .withExpiresAt(expiresAt)
                .withClaim("username", user.getUsername())
                .withClaim("user_id", user.getId())
                .withClaim("login_type", loginType)
                .withArrayClaim("services", getAvailableServices(user))
                .sign(getAlgorithm());

        return new JWTToken(token, getExpirationSeconds(), DateTimeUtils.of(expiresAt));
    }

    @Override
    public DecodedJWT decode(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm())
                .withIssuer(JWT_ISSUER)
                .build();
        return verifier.verify(token);
    }

    @Override
    public User getUser(DecodedJWT token) {
        Integer userId = token.getClaim("user_id").asInt();
        return userRepository.findById(userId.longValue())
                .orElseThrow(() -> new UsernameNotFoundException("No find user by user id in token"));
    }

    private Date buildExpirationTime() {
        return new Date(System.currentTimeMillis() + getExpirationSeconds() * 1000L);
    }

    private int getExpirationSeconds() {
        return tokenExpiredHours.intValue() * 3600;
    }

    private Algorithm getAlgorithm() {
        Algorithm algorithm = Algorithm.HMAC256(oauth2SymmetricSecret.getBytes());
        return algorithm;
    }

    private String[] getAvailableServices(User user) {
        List<String> availableServices = permissionService.findAllServicesByUserId(user.getId());
        String[] arr = new String[availableServices.size()];
        availableServices.toArray(arr);
        return arr;
    }

}