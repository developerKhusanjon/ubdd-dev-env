package uz.ciasev.ubdd_service.config.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class JWTToken {

    private String token;

    private int expiresAt;

    private LocalDateTime expiresAtTime;
}
