package uz.ciasev.ubdd_service.config.security;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TokenDTO {

    private String token;

    private int expiresAt;

    private LocalDateTime expiresAtTime;

    private String type = "Bearer";

    public TokenDTO(JWTToken jwtToken) {
        this.token = jwtToken.getToken();
        this.expiresAt = jwtToken.getExpiresAt();
        this.expiresAtTime = jwtToken.getExpiresAtTime();
    }
}
