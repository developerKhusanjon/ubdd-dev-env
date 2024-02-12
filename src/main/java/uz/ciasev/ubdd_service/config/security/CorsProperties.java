package uz.ciasev.ubdd_service.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private long maxAge;
    private boolean allowedCredentials;
    private List<String> allowedMethods;
    private List<String> allowedOrigins;
    private List<String> allowedHeaders;
}
